package com.anilari.order.utill

fun maskCardNumber(number:String,maskLengthStart:Int,maskLengthEnd:Int): String {
    val sb = StringBuilder()

    for ( i in number.indices) {
         if(i<maskLengthStart || i>maskLengthEnd)
         {
             sb.append(number[i])
         }
        else
         {
             sb.append("*")
         }

    }
    return sb.toString();
}
