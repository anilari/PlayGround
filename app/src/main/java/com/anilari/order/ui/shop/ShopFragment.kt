package com.anilari.order.ui.shop

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.anilari.order.R
import com.anilari.order.adapter.BestSellingAdapter
import com.anilari.order.adapter.ExclusiveAdapter
import com.anilari.order.adapter.GroceriesAdapter
import com.anilari.order.listener.OnClickItemAndAdd
import com.anilari.order.model.product.ProductEntity
import com.anilari.order.ui.activity.MainActivity
import com.anilari.order.ui.detailproduct.DetailProductActivity
import com.anilari.order.ui.product.ProductActivity
import com.anilari.order.utill.Constant
import com.anilari.order.utill.ProductSavedType
import kotlinx.android.synthetic.main.fragment_shop.*
import org.koin.android.viewmodel.ext.android.viewModel


class ShopFragment : Fragment(R.layout.fragment_shop) {

    private val exclusiveAdapter: ExclusiveAdapter by lazy {
        ExclusiveAdapter()
    }

    private val groceriesAdapter: GroceriesAdapter by lazy {
        GroceriesAdapter {
            startActivity(Intent(activity, ProductActivity::class.java))
        }
    }

    private val bestSellingAdapter: BestSellingAdapter by lazy {
        BestSellingAdapter()
    }

    private val viewModel: ShopViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intentSearch()
        showBanner()

        setListExclusive()
        observeExclusiveOffer()


        viewModel.showDataProducts()
     //   viewModel.showDataBestSelling()
     //   viewModel.showDataGroceries()

    }

    private fun intentSearch() {
        tv_search.setOnClickListener {
            (activity as MainActivity).navigateExplore()
        }
    }

    private fun showBanner() {
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.banner))
        imageList.add(SlideModel(R.drawable.banner2))
        imageList.add(SlideModel(R.drawable.banner3))

        image_slider.setImageList(imageList, ScaleTypes.CENTER_CROP)
    }

    private fun observeExclusiveOffer() {
        viewModel.products.observe(viewLifecycleOwner) {
            exclusiveAdapter.setDataAdapter(it)
        }
    }

    private fun setListExclusive() {
        rv_exclusive_offer.setHasFixedSize(true)
        rv_exclusive_offer.adapter = exclusiveAdapter
        exclusiveAdapter.onClickListener = object : OnClickItemAndAdd {
            override fun onClick(productEntity: ProductEntity) {
                toProducts(productEntity)
            }

            override fun onClickAdd(productEntity: ProductEntity) {
                addProductToCart(productEntity, ProductSavedType.CART)
            }
        }
    }




    private fun toProducts(productEntity: ProductEntity) {
        val intent = Intent(activity, DetailProductActivity::class.java)
        intent.putExtra(Constant.DATA, productEntity)
        startActivity(intent)
    }

    private fun addProductToCart(productEntity: ProductEntity, cart: Int) {

        if(viewModel.addToCart(productEntity, ProductSavedType.CART))
        {
            Toast.makeText(activity, "Ürün karta eklendi!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(activity, "Maksimum sayıya ulaşıldı!", Toast.LENGTH_SHORT).show()
        }
    }
    
}