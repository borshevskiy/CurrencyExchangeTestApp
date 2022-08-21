package com.borshevskiy.currencyexchangetestapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.borshevskiy.currencyexchangetestapp.data.database.CurrencyDao
import com.borshevskiy.currencyexchangetestapp.data.mapper.CurrencyMapper
import com.borshevskiy.currencyexchangetestapp.data.network.ApiService
import com.borshevskiy.currencyexchangetestapp.domain.Currency
import com.borshevskiy.currencyexchangetestapp.domain.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: CurrencyMapper,
    private val dao: CurrencyDao
) : CurrencyRepository {
    
    override fun getAllCurrenciesList(): LiveData<List<Currency>> {
        return Transformations.map(dao.readCurrencies()) {
            it.map { mapper.mapDbModelToCurrency(it) }
        }
    }

    override fun getFavoriteCurrenciesList(): LiveData<List<Currency>> {
        return Transformations.map(dao.readFavoriteCurrencies()) {
            it.map { mapper.mapFavDbModelToCurrency(it) }
        }
    }

    override suspend fun saveToFavorites(currency: Currency) {
        dao.insertFavoriteCurrencies(mapper.mapCurrencyToFavDbModel(currency))
        dao.insertCurrency(mapper.mapCurrencyToDbModel(currency))
    }

    override suspend fun removeFromFavorites(currency: Currency) {
        dao.deleteFavoriteCurrency(mapper.mapCurrencyToFavDbModel(currency))
    }

    override suspend fun loadData() {
        val response = apiService.getCurrenciesInfo()
        if (response.isSuccessful) { dao.insertCurrencies(mapper.mapDtoToDbModel(response.body()!!)) }
    }
}