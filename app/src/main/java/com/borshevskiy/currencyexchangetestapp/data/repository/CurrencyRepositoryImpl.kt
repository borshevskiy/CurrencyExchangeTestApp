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

    override suspend fun getAllCurrenciesList(query: String) {
        val response = apiService.getCurrenciesInfo(baseCurrency = query)
        if (response.isSuccessful) { dao.insertCurrencies(mapper.mapDtoToDbModel(response.body()!!)) }
    }

    override suspend fun getFavoriteCurrenciesList(query: String, list: String) {
        val response = apiService.getFavoriteCurrenciesInfo(baseCurrency = query, currencies = list)
        if (response.isSuccessful) { dao.insertFavoriteCurrencies(mapper.mapDtoToDbModel(response.body()!!)) }
    }

    override suspend fun saveAndRemoveFromFavorites(currency: Currency) {
        dao.updateCurrency(mapper.mapCurrencyToDbModel(currency))
        if (!currency.isFavorite) {
            dao.insertFavoriteCurrency(mapper.mapCurrencyToFavDbModel(currency))
        } else {
            dao.deleteFavoriteCurrency(mapper.mapCurrencyToFavDbModel(currency))
        }
    }

    override fun readAndFilterCurrencies(filter: String): LiveData<List<Currency>> {
        return when (filter) {
            "nameAsc" -> Transformations.map(dao.readCurrenciesNameAsc()) { it.map { mapper.mapDbModelToCurrency(it) } }
            "nameDesc" -> Transformations.map(dao.readCurrenciesNameDesc()) { it.map { mapper.mapDbModelToCurrency(it) } }
            "valueAsc" -> Transformations.map(dao.readCurrenciesValueAsc()) { it.map { mapper.mapDbModelToCurrency(it) } }
            "valueDesc" -> Transformations.map(dao.readCurrenciesValueDesc()) { it.map { mapper.mapDbModelToCurrency(it) } }
            else -> { return Transformations.map(dao.readCurrenciesNameAsc()) { it.map { mapper.mapDbModelToCurrency(it) } } }
        }
    }

    override fun readAndFilterFavoriteCurrencies(filter: String): LiveData<List<Currency>> {
        return when (filter) {
            "nameAsc" -> Transformations.map(dao.readFavoriteCurrenciesNameAsc()) { it.map { mapper.mapFavDbModelToCurrency(it) } }
            "nameDesc" -> Transformations.map(dao.readFavoriteCurrenciesNameDesc()) { it.map { mapper.mapFavDbModelToCurrency(it) } }
            "valueAsc" -> Transformations.map(dao.readFavoriteCurrenciesValueAsc()) { it.map { mapper.mapFavDbModelToCurrency(it) } }
            "valueDesc" -> Transformations.map(dao.readFavoriteCurrenciesValueDesc()) { it.map { mapper.mapFavDbModelToCurrency(it) } }
            else -> { return Transformations.map(dao.readFavoriteCurrenciesNameAsc()) { it.map { mapper.mapFavDbModelToCurrency(it) } } }
        }
    }
}