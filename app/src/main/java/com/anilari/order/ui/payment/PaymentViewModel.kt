package com.anilari.order.ui.payment


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anilari.order.data.Repository
import com.anilari.order.model.purchase.PurchaseEntity
import com.anilari.order.utill.CardType

class PaymentViewModel(val repository: Repository) : ViewModel() {
    val cardHolderName = MutableLiveData("")
    val cardNumber = MutableLiveData("")
    val cardNumberPlaceholder = MutableLiveData("**** **** **** ****")
    val cardExpiry = MutableLiveData("")
    val cardCVV = MutableLiveData("")
    val cardType = MutableLiveData(CardType.NO_TYPE)
    val checkOut = MutableLiveData(false)
    fun insertPurchase(purchaseEntity: PurchaseEntity)
    {
        repository.insertPurchase(purchaseEntity)
    }


    fun getPurchases(): List<PurchaseEntity> {
        return repository.getPurchases()
    }

    fun deleteAllProductsFromCard(){
         repository.dataBase.productDao().deleteAll()
    }

    fun getLastPurchaseId(): Int {
        return repository.getPurchases().lastIndex
    }

}