package com.borshevskiy.currencyexchangetestapp.domain

import androidx.lifecycle.LiveData

interface CurrencyRepository {

    fun getAllCurrenciesList(): LiveData<List<Currency>>

    fun getFavoriteCurrenciesList(): LiveData<List<Currency>>

    suspend fun saveToFavorites(currency: Currency)

    suspend fun removeFromFavorites(currency: Currency)

    suspend fun loadData()
}