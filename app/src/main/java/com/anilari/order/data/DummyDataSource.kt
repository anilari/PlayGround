package com.anilari.order.data

import com.anilari.order.R
import com.anilari.order.model.product.ProductEntity
import io.reactivex.Observable

class DummyDataSource {

    fun getProducts(): Observable<ArrayList<ProductEntity>> {

        val dummy1 = ProductEntity(name = "Kalem", description = "1 adet, Fiyat",
            price = 9.58,
            picture = R.drawable.pen,
            id = 1,
            qty = 5

        )
        val dummy2 = ProductEntity(name = "Kağıt", description = "1 adet, Fiyat",
            price = 0.61,
            picture = R.drawable.paper,
            id = 2
        )
        val dummy3 = ProductEntity(name = "Silgi", description = "1 adet, Fiyat",
            price = 30.00,
            picture = R.drawable.eraser,
            id = 3
        )
        val dummy4 = ProductEntity(name = "Defter", description = "1 adet, Fiyat",
            price = 40.00,
            picture = R.drawable.notebook,
            id = 4
        )
        val dummy5 = ProductEntity(name = "Kitap", description = "1 adet, Fiyat",
            price = 50.82,
            picture = R.drawable.book,
            id = 14
        )

        val data = listOf(dummy1, dummy2, dummy3, dummy4, dummy5)
        return Observable.just(ArrayList(data))

    }


    fun getSearchData(qword: String?): Observable<List<ProductEntity>> {
        val listData = listOf(getProducts())
        val resultData = mutableListOf<ProductEntity>()

        return Observable.merge(listData).doOnNext{
            resultData.addAll(it)
        }
            .map {
                return@map resultData.filter {
                    it.name.contains(qword.orEmpty(), true)
                }
            }
    }

}