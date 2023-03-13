package com.example.data

interface FileProvider {
    fun <T> fromJson(fileName: String, type: Class<T>): T?

    fun saveAsJsonFile(fileName: String, data: Any): Boolean
}