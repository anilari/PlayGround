package com.anilari.order.ui.explore

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.anilari.order.R
import com.anilari.order.adapter.ExploreAdapter
import com.anilari.order.listener.OnClickItemAndAdd
import com.anilari.order.model.product.ProductEntity
import com.anilari.order.ui.detailproduct.DetailProductActivity
import com.anilari.order.utill.Constant
import kotlinx.android.synthetic.main.fragment_explore.*
import org.koin.android.viewmodel.ext.android.viewModel


class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private val viewModel: ExploreViewModel by viewModel()

    private val exploreAdapter: ExploreAdapter by lazy {
        ExploreAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_search.requestFocus()
        searchProduct()
        observeSearch()
        setListSearch()

    }

    private fun searchProduct() {
        et_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(qword: String?): Boolean {
                et_search.clearFocus()
                viewModel.requestProductQuery(qword)
                return true
            }

            override fun onQueryTextChange(qword: String?): Boolean {
                viewModel.requestProductQuery(qword)
                return false
            }
        })
    }

    private fun observeSearch() {
        viewModel.searchProduct.observe(viewLifecycleOwner, {
            exploreAdapter.setDataAdapter(it)
        })
    }

    private fun setListSearch() {
        rv_explore.setHasFixedSize(true)
        rv_explore.adapter = exploreAdapter
        rv_explore.layoutManager = GridLayoutManager(activity, 2)
        exploreAdapter.onClickListener = object : OnClickItemAndAdd {
            override fun onClick(productEntity: ProductEntity) {
                navigationToDetail(productEntity)
            }
            override fun onClickAdd(productEntity: ProductEntity) {
                addProductToCart(productEntity)
            }
        }
    }

    private fun navigationToDetail(productEntity: ProductEntity) {
        val intent = Intent(activity, DetailProductActivity::class.java)
        intent.putExtra(Constant.DATA, productEntity)
        startActivity(intent)
    }

    private fun addProductToCart(productEntity: ProductEntity) {
        if (productEntity.qty == 0) {
            viewModel.addToCart(productEntity)
            Toast.makeText(activity, "??r??n karta eklendi!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.removeProductCart(productEntity)
        }
    }

}


