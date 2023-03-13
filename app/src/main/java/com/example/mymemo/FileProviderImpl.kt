package com.example.mymemo

import android.content.Context
import com.example.data.FileProvider
import com.example.model.Memo
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class FileProviderImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val moshi: Moshi,
) : FileProvider {
    override fun <T> fromJson(fileName: String, type: Class<T>): T? {
        return try {
            val inputStream = context.openFileInput(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            moshi.adapter(type).fromJson(jsonString)
        } catch (e: FileNotFoundException) {
            null
        }
    }

    override fun saveAsJsonFile(fileName: String, data: Any): Boolean {
        val jsonString = moshi.adapter(data.javaClass).toJson(data)
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
        return true
    }
}