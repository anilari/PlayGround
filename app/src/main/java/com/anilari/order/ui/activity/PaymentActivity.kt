package com.anilari.order.ui.activity

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anilari.order.R
import com.anilari.order.databinding.ActivityPaymentBinding
import com.anilari.order.extensions.Extensions.setCreditCardTextWatcher
import com.anilari.order.extensions.Extensions.setExpiryDateFilter
import com.anilari.order.model.purchase.PurchaseEntity
import com.anilari.order.ui.payment.PaymentViewModel
import com.anilari.order.utill.CardType
import com.anilari.order.utill.maskCardNumber
import com.anilari.order.utill.valid
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import render.animations.Fade
import render.animations.Flip
import render.animations.Render
import render.animations.Slide
import java.text.SimpleDateFormat
import java.util.*


class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModel()


    lateinit var flipLeftIn: AnimatorSet
    lateinit var flipLeftOut: AnimatorSet
    lateinit var flipRightOut: AnimatorSet
    lateinit var flipRightIn: AnimatorSet

    lateinit var animatedVectorDrawable: AnimatedVectorDrawable

    private var isFront = true
    private var amount = ""
    private var cardValid: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding.apply {
            lifecycleOwner = this@PaymentActivity
            viewModel = this@PaymentActivity.viewModel
            frontSide.viewModel = this@PaymentActivity.viewModel
            frontSide.lifecycleOwner = this@PaymentActivity
            backSide.viewModel = this@PaymentActivity.viewModel
            backSide.lifecycleOwner = this@PaymentActivity
        }

        setupAnimation()
        setupViews()
        amount = intent.getStringExtra("total_price").toString()
        tv_amount.text = "TUTAR: $amount"


    }

    private fun setupAnimation() {
        val scale = applicationContext.resources.displayMetrics.density
        binding.frontSide.frontSide.cameraDistance = 16000 * scale
        binding.backSide.backSide.cameraDistance = 16000 * scale

        flipLeftIn =
            AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_in) as AnimatorSet
        flipLeftOut =
            AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_out) as AnimatorSet
        flipRightIn =
            AnimatorInflater.loadAnimator(this, R.animator.card_flip_right_in) as AnimatorSet
        flipRightOut =
            AnimatorInflater.loadAnimator(this, R.animator.card_flip_right_out) as AnimatorSet
    }

    private fun flipCard() {
        if (isFront) {
            flipRightIn.setTarget(binding.backSide.backSide)
            flipRightOut.setTarget(binding.frontSide.frontSide)
            flipRightIn.start()
            flipRightOut.start()
        } else {
            flipLeftIn.setTarget(binding.frontSide.frontSide)
            flipLeftOut.setTarget(binding.backSide.backSide)
            flipLeftIn.start()
            flipLeftOut.start()
        }
        isFront = !isFront
    }

    private fun setupViews() {
        binding.etCardNumber.setCreditCardTextWatcher()
        binding.etExpiryDate.setExpiryDateFilter()
        binding.checkoutBtn.isEnabled = cardValid
        val purchases = viewModel.getPurchases()
        println(purchases);
        viewModel.cardNumber.observe(this
        ) {
                value ->
            replacePlaceholder(value)
            checkCardType(value)
            cardValid = valid(value)
            if(value.length == 16)
            {
                if(!cardValid)
                {
                    Toast.makeText(this,"Card Number is not valid!", Toast.LENGTH_SHORT).show()
                    binding.checkoutBtn.isEnabled = cardValid
                }
                else
                {
                    Toast.makeText(this,"Card Number is valid!", Toast.LENGTH_SHORT).show()
                    binding.checkoutBtn.isEnabled = cardValid
                }
            }


        }

        binding.etCardCvv.setOnFocusChangeListener { view, b ->
            if (b) {
                flipCard()
            } else {
                MainScope().launch {
                    delay(400)
                    flipCard()
                }
            }
        }
    }

    private fun replacePlaceholder(value: String) {
        var placeholderString = "**** **** **** ****"

        value.forEach {
            val sb = StringBuilder(placeholderString)
            val firstIndex = placeholderString.indexOf("*")
            sb.setCharAt(firstIndex, it)
            placeholderString = sb.toString()
        }
        viewModel.cardNumberPlaceholder.postValue(placeholderString)
    }

    private fun checkCardType(value: String) {
        if (value.length < 4) {
            viewModel.cardType.value = CardType.NO_TYPE
            return
        }

        if (value.startsWith("4")) {
            viewModel.cardType.value = CardType.VISA_CARD
        } else {
            viewModel.cardType.value = CardType.MASTER_CARD
        }
    }

    private fun checkOut() {
        viewModel.checkOut.value = true
        var render = Render(this).also { it.setAnimation(Slide.InUp(binding.tempImg)) }
        render.start()

        MainScope().launch {
            delay(1000)
            binding.tvCheckOutMade.visibility = View.VISIBLE
            binding.btnReturn.visibility = View.VISIBLE
           // binding.frontSide.visible = false
            val purchase = PurchaseEntity(null,binding.etCardNumber.text.toString(),  System.currentTimeMillis(),false)
            viewModel.insertPurchase(purchase)
            viewModel.deleteAllProductsFromCard()
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            binding.tvCheckOutMade.text =  "#"+viewModel.getLastPurchaseId()+" NO'LU SİPARİŞİNİZ \n"+
                    maskCardNumber(binding.etCardNumber.text.toString(),6,11) +" NO'LU KREDİ KARTIYLA \n"+
                    amount +" TUTARINDA \n"+
                    currentDate +" TARİHİNDE GERÇEKLEŞMİŞTİR."
            binding.btnReturn.setOnClickListener {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }


            var textRender =
                Render(this@PaymentActivity).also { it.setAnimation(Fade.InUp(binding.tvCheckOutMade)) }
            textRender.start()
            delay(3000)

//            render = Render(this@MainActivity).also { it.setAnimation(Fade.Out(binding.tempImg)) }
//            render = Render(this@PaymentActivity).also { it.setAnimation(Flip.OutX(binding.tempImg)) }
//            textRender =
//                Render(this@PaymentActivity).also { it.setAnimation(Fade.Out(binding.tvCheckOutMade)) }
 //           render.start()
        //       textRender.start()



        }

        MainScope().launch {
            delay(1000)
            val drawable = binding.done.drawable
            animatedVectorDrawable = drawable as AnimatedVectorDrawable
            animatedVectorDrawable.start()
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.checkout_btn -> checkOut()
            R.id.btn -> flipCard()
        }
    }
}