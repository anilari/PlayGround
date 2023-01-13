package com.anilari.order.model.product

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anilari.order.utill.ProductSavedType
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="pk")
    val pk: Int = 0,
    @ColumnInfo(name="id")
    val id: Int = 0,
    @ColumnInfo(name= "name")
    val name: String = "",
    @ColumnInfo(name= "picture")
    val picture: Int = 0,
    @ColumnInfo(name= "description")
    val description: String = "",
    @ColumnInfo(name= "price")
    val price: Double = 0.0,
    @ColumnInfo(name= "qty")
    val qty: Int = 0,
    @ColumnInfo(name= "maxQty")
    var maxQty: Int = 5,
    @ColumnInfo(name = "type")
    val type: Int = ProductSavedType.FAV

): Parcelable{

    val priceToQty get() = qty * price
}
