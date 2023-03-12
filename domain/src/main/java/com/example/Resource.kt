package com.example

sealed class Resource<out R>(private val _data: R? = null) {
    data class Success<out T>(val dataModel: T) : Resource<T>(_data = dataModel)
    data class Error(val error: Exception? = null) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$dataModel]"
            is Error -> "Error[exception=$error]"
        }
    }
}
