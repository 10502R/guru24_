package com.example.guru24

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    val name: String,
    val category: String,
    val building: String,
    val address: String,
    val phone: String,
    val hours: String
) : Parcelable

