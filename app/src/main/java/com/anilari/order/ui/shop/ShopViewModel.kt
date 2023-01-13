package com.anilari.order.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anilari.order.data.Repository
import com.anilari.order.model.product.ProductEntity
import io.reactivex.disposables.CompositeDisposable

class ShopViewModel(private val repository : Repository) : ViewModel() {

    private val _products = MutableLiveData<ArrayList<ProductEntity>>()
    val products: LiveData<ArrayList<ProductEntity>> = _products

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }


    fun showDataProducts() {
        val exclusiveOfferDisposable = repository.getProducts()
            .doOnSubscribe { }
            .doFinally { }
            .subscribe(
                { _products.postValue(it) },
                { _errorMessage.postValue(it.localizedMessage) })
        compositeDisposable.add(exclusiveOfferDisposable)
    }


    fun addToCart(productEntity: ProductEntity, cart: Int): Boolean {
        return repository.addToCart(productEntity)
    }


}