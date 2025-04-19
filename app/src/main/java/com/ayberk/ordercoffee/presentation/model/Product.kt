package com.ayberk.ordercoffee.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: Int,
    val categoryName: String
) : Parcelable
