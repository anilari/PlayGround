package com.anilari.order.model.purchase

import androidx.room.*
import java.security.PrivateKey

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchase")
    fun getAll():List<PurchaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(purchaseEntity: PurchaseEntity)

    @Delete
    fun delete(purchaseEntity: PurchaseEntity)

    @Query("UPDATE purchase Set canceled = 1 Where pk = :pk")
    fun setCanceled(pk: Int)

    @Update
    fun update(purchaseEntity: PurchaseEntity)
}