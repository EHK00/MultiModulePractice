package com.example.mymemo

import android.content.Context
import com.example.data.FileProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileProviderImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val moshi: Moshi,
) : FileProvider {
    override fun <T> fromJson(fileName: String, type: Class<T>): T? {
        val inputStream = context.assets.open(fileName)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return moshi.adapter(type).fromJson(jsonString)
    }
}