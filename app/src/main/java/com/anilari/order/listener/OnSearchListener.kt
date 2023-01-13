package com.anilari.order.listener

import com.anilari.order.model.product.ProductEntity

interface OnSearchListener {
    fun onSearch(productEntity: ProductEntity)
}