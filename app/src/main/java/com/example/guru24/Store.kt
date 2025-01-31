package com.example.guru24

import java.io.Serializable

data class Store(
    val name: String,
    val category: String,
    val building: String,
    val address: String,
    val phone: String,
    val hours: String,
    val image: Int,
    val menu: Int
) : Serializable