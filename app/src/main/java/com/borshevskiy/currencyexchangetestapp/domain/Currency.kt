package com.borshevskiy.currencyexchangetestapp.domain

data class Currency(
    val name: String,
    val value: String,
    val isFavorite: Boolean = false)