package com.example.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Memo(
    val id: String,
    val subject: String,
    val content: String,
): Parcelable