package com.zaidzakir.cryptocurrencytracker.util

/**
 *Created by Zaid Zakir
 */
sealed class Resource<T>(val data:T? , val message:String?) {
    class Success<T>(data: T):Resource<T>(data,null)
    class Error<T>(message: String?):Resource<T>(null, message)
    class Loading<T>(data: T? = null ,message: String? = null):Resource<T>(data, message)
}