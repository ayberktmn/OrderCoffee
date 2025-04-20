package com.ayberk.ordercoffee.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteProduct")
data class FavoriteProduct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "imageUrl") val imageUrl: Int,
    @ColumnInfo(name = "category") val category: String
)

