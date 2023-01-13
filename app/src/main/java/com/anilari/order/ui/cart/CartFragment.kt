package com.anilari.order.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anilari.order.R
import com.anilari.order.adapter.CartAdapter
import com.anilari.order.listener.OnClickItemAddRemove
import com.anilari.order.listener.OnTotalChange
import com.anilari.order.model.product.ProductEntity
import com.anilari.order.ui.activity.PaymentActivity
import com.anilari.order.ui.detailproduct.DetailProductActivity
import com.anilari.order.utill.Constant
import com.anilari.order.utill.ProductSavedType
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DecimalFormat


class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel: CartViewModel by viewModel()

    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(object : OnTotalChange{
            override fun onTotalChange(total: Double) {
                val dec = DecimalFormat("#.###")
                val priceTry = dec.format(total)

                tv_total_price.text = "$priceTry TL"
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeCart()
        setListCart()
        viewModel.loadDataCart()

        btn_favorite.setOnClickListener() {
                val intent = Intent(activity, PaymentActivity::class.java)
                intent.putExtra("total_price", tv_total_price.text)
                startActivity(intent)
                activity?.finish()
        }

    }

    private fun observeCart() {
        viewModel.cartProduct.observe(viewLifecycleOwner) {
            cartAdapter.setDataAdapter(it)
        }
    }

    private fun setListCart() {

        rv_cart.setHasFixedSize(true)
        rv_cart.adapter = cartAdapter
        cartAdapter.onClickListener = object : OnClickItemAddRemove {
            override fun onClick(productEntity: ProductEntity) {
                val intent = Intent(activity, DetailProductActivity::class.java)
                intent.putExtra(Constant.DATA, productEntity)
                startActivity(intent)
            }

            override fun onClickAdd(productEntity: ProductEntity) {
                addQtyProduct(productEntity)
            }

            override fun onClickSubstract(productEntity: ProductEntity) {
                subtractQtyProduct(productEntity)
            }

            override fun onClickRemove(productEntity: ProductEntity) {
                removeFromCart(productEntity)
            }
        }

    }

    private fun addQtyProduct(productEntity: ProductEntity) {
        viewModel.addProduct(productEntity)
        viewModel.loadDataCart()
    }

    private fun subtractQtyProduct(productEntity: ProductEntity) {
        viewModel.subtractProduct(productEntity)
        viewModel.loadDataCart()
    }

    private fun removeFromCart(productEntity: ProductEntity) {
        viewModel.removeProduct(productEntity, ProductSavedType.CART)
        Toast.makeText(activity, "Product removed from cart", Toast.LENGTH_SHORT).show()
        viewModel.loadDataCart()
    }


}