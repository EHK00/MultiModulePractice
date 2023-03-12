package com.example.common

import android.content.Context

interface Navigator {
    fun gotoCreateMemoScreen(context: Context, id: String?)
    fun gotoMemoListScreen(context: Context)
}