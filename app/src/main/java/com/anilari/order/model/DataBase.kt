package com.anilari.order.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anilari.order.AppController
import com.anilari.order.model.product.ProductEntity
import com.anilari.order.model.product.ProductDao
import com.anilari.order.model.purchase.PurchaseDao
import com.anilari.order.model.purchase.PurchaseEntity

@Database(entities = [ProductEntity::class,PurchaseEntity::class], version = 1)
abstract class DataBase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        private var INSTANCE: DataBase? = null

        fun getInstance(): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        AppController.getInstance().applicationContext,
                        DataBase::class.java, "groceries_stotre.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}