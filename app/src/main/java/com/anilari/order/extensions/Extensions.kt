package com.anilari.order.extensions

import com.anilari.order.utill.CreditCardExpiryInputFilter
import com.anilari.order.utill.CreditCardFormatTextWatcher
import com.google.android.material.textfield.TextInputEditText

object Extensions {

    fun TextInputEditText.setCreditCardTextWatcher() {
        val tv =
            CreditCardFormatTextWatcher(this)
        this.addTextChangedListener(tv)
    }

    fun TextInputEditText.setExpiryDateFilter() {
        this.filters = arrayOf(CreditCardExpiryInputFilter())
    }

}

