package com.example.data

interface FileProvider {
    fun <T> fromJson(fileName: String, type: Class<T>): T?
}