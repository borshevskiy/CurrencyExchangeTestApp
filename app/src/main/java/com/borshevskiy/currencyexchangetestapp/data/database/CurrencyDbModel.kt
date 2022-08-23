package com.borshevskiy.currencyexchangetestapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_currencies_list")
data class CurrencyDbModel(
    @PrimaryKey
    val name: String,
    val value: String,
    val isFavorite: Boolean
)