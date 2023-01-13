package com.anilari.order.model.purchase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "purchase")
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="pk")
    val pk: Int? = null,
    @ColumnInfo(name= "description")
    val description: String,
    @ColumnInfo(name= "date")
    val date: Long,
    @ColumnInfo(name= "canceled")
    val canceled: Boolean,

    ): Parcelable{

    val descriptionString get() = description
}
