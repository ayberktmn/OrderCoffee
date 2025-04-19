package com.ayberk.ordercoffee.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: Int // Drawable resource ID gibi görünüyor
) : Parcelable
