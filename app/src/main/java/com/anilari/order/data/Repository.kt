package com.anilari.order.data

import com.anilari.order.model.DataBase
import com.anilari.order.model.product.ProductEntity
import com.anilari.order.model.purchase.PurchaseEntity
import com.anilari.order.utill.ProductSavedType

class Repository(val dummyDataSource: DummyDataSource, val dataBase: DataBase) {

    fun getProducts() = dummyDataSource.getProducts()
   // fun getGroceries() = dummyDataSource.getGroceries()
   // fun getBestSelling() = dummyDataSource.getBestSelling()
   // fun getBeverages() = dummyDataSource.getBeverages()
    fun getSearchData(qword: String?) = dummyDataSource.getSearchData(qword)

    fun addToFav(productEntity: ProductEntity) {
        val prod = productEntity.copy(type = ProductSavedType.FAV)
        dataBase.productDao().insert(prod)
    }

    fun addToCart(productEntity: ProductEntity, qty: Int = 1 ): Boolean {
        val prodList = dataBase.productDao().getById(productEntity.id, ProductSavedType.CART)

        if (prodList.isNotEmpty()) {
            if(prodList[0].qty < prodList[0].maxQty)
            {
                val prod = prodList[0].copy(
                    qty = prodList[0].qty + qty,
                    type = ProductSavedType.CART
                )
                dataBase.productDao().delete(productEntity)
                dataBase.productDao().insert(prod)
                return true
            }
            else
            {
                return false;
            }
        } else {
            val prod = productEntity.copy(
                qty = qty,
                type = ProductSavedType.CART
            )
            dataBase.productDao().insert(prod)
            return true
        }
    }

    fun subtractCart(productEntity: ProductEntity, qty: Int = 1) {
        if (productEntity.qty > 1) {
            val prod = productEntity.copy(
                qty = productEntity.qty - qty,
            )
            dataBase.productDao().delete(productEntity)
            dataBase.productDao().insert(prod)
        } else {
            dataBase.productDao().delete(productEntity)
        }
    }


    fun removeProductFav(id: Int, type: Int) {
        dataBase.productDao().deleteById(id, ProductSavedType.FAV)
    }

    fun removeProductCart(id: Int, type: Int) {
        dataBase.productDao().deleteById(id, ProductSavedType.CART)
    }

    fun checkProduct(id: Int): Boolean {
        return dataBase.productDao().getById(id, ProductSavedType.FAV).isNotEmpty()
    }

    fun getAllDb(type: Int): List<ProductEntity> {
        return dataBase.productDao().getAll(type).orEmpty()
    }

    fun insertPurchase(purchaseEntity: PurchaseEntity)
    {
      dataBase.purchaseDao().insert(purchaseEntity)
    }

    fun getPurchases(): List<PurchaseEntity> {
        return dataBase.purchaseDao().getAll().orEmpty()
    }

}
