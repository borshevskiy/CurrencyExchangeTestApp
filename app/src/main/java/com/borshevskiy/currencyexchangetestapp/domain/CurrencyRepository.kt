package com.borshevskiy.currencyexchangetestapp.domain

import androidx.lifecycle.LiveData

interface CurrencyRepository {

    suspend fun getAllCurrenciesList(query: String)

    suspend fun getFavoriteCurrenciesList(query: String, list: String = "")

    suspend fun saveAndRemoveFromFavorites(currency: Currency)

    fun readAndFilterCurrencies(filter: String): LiveData<List<Currency>>

    fun readAndFilterFavoriteCurrencies(filter: String): LiveData<List<Currency>>
}