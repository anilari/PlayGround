package com.anilari.order.bindings

import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.anilari.order.R
import com.anilari.order.utill.CardType
import com.bumptech.glide.Glide

object Bindings {
    @RequiresApi(Build.VERSION_CODES.M)
    @JvmStatic
    @BindingAdapter("cardType")
    fun changeBackGround(view: View, cardType: CardType) {
        when (cardType) {
            CardType.NO_TYPE -> {
                view.setBackgroundColor(view.context.getColor(R.color.blank_card))
            }
            CardType.VISA_CARD -> {
                view.setBackgroundColor(view.context.getColor(R.color.visa_card))
            }
            CardType.MASTER_CARD -> {
                view.setBackgroundColor(view.context.getColor(R.color.master_card))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("cardType")
    fun setImage(imageView: ImageView, cardType: CardType) {
        when (cardType) {
            CardType.NO_TYPE -> {
                imageView.visibility = View.GONE
            }
            CardType.VISA_CARD -> {
                imageView.visibility = View.VISIBLE
                Glide.with(imageView.context).load(R.drawable.ic_visa_logo).centerInside()
                    .into(imageView)
            }
            CardType.MASTER_CARD -> {
                imageView.visibility = View.VISIBLE
                Glide.with(imageView.context).load(R.drawable.ic_master_card_logo).centerInside()
                    .into(imageView)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}