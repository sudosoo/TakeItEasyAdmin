package com.sudosoo.takeItEasyAdmin.common.service

object CommonService {
    fun checkNotNullData(value : Any?, message : String){
        try{ requireNotNull(value) }
        catch (e : IllegalArgumentException){
            throw IllegalArgumentException("DataException : $message")
        }
    }

}
