package com.example.mymemo.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import com.example.data.local.MemoLocalDataSource
import com.example.model.Memo
import com.example.model.ShortenMemo
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyMemoSharedPref(
    context: Context,
    key: String,
    val moshi: Moshi,
) {
    val pref: SharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    inline fun <reified T> getParcelable(key: PrefKey): T? = pref.getString(key.name, null)?.let { json ->
        try {
            val adapter = moshi.adapter(T::class.java)
            adapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    fun setParcelable(key: PrefKey, value: Parcelable?) {
        val data = value?.let {
            val adapter = moshi.adapter(value.javaClass)
            adapter.toJson(value)
        }
        pref.edit().putString(key.name, data).apply()
    }

    fun setParcelableCollection(key: PrefKey, value: Collection<Parcelable?>) {
        val data = value.let {
            val adapter = moshi.adapter(value.javaClass)
            adapter.toJson(value)
        }
        pref.edit().putString(key.name, data).apply()
    }


    enum class PrefKey {
        MemoList,
    }
}