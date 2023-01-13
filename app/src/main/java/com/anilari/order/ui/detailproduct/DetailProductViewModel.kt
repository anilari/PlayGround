package com.anilari.order.ui.detailproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anilari.order.data.Repository
import com.anilari.order.model.product.ProductEntity
import com.anilari.order.utill.ProductSavedType

class DetailProductViewModel(val repository: Repository) : ViewModel() {

    private val _detailProduct = MutableLiveData<List<ProductEntity>>()
    val detailProduct: LiveData<List<ProductEntity>> = _detailProduct

    private val _isfavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isfavorited

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var dataFavorite: ArrayList<ProductEntity> = ArrayList()

    fun loadDataDetail() {
        dataFavorite.clear()
        dataFavorite.addAll(repository.getAllDb(ProductSavedType.FAV))
        _detailProduct.postValue(dataFavorite)
    }

    fun saveProduct(productEntity: ProductEntity) {
        repository.addToFav(productEntity)
    }

    fun removeProduct(productEntity: ProductEntity) {
         repository.removeProductFav(productEntity.id, ProductSavedType.FAV)
    }

    fun checkProduct(productEntity: ProductEntity) {
        _isfavorited.postValue(repository.checkProduct(productEntity.id))
    }

    fun addToCart(productEntity: ProductEntity, cart: Int): Boolean {
        return repository.addToCart(productEntity)
    }


}