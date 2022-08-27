package com.borshevskiy.currencyexchangetestapp.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.borshevskiy.currencyexchangetestapp.data.database.CurrencyDao
import com.borshevskiy.currencyexchangetestapp.data.mapper.CurrencyMapper
import com.borshevskiy.currencyexchangetestapp.data.network.ApiService
import com.borshevskiy.currencyexchangetestapp.domain.Currency
import com.borshevskiy.currencyexchangetestapp.domain.CurrencyRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val mapper: CurrencyMapper,
    private val dao: CurrencyDao
) : CurrencyRepository {

    private val preferences = context.getSharedPreferences("app_settings", MODE_PRIVATE)

    override suspend fun getAllCurrenciesList(query: String) {
        val response = apiService.getCurrenciesInfo(baseCurrency = query)
        if (response.isSuccessful) {
            dao.insertCurrencies(mapper.mapDtoToDbModel(response.body()!!))
            if (preferences.contains("FAVORITES")) {
                val favList = preferences.getString("FAVORITES", "")!!.removeSuffix(",").split(",")
                favList.forEach { dao.backupFavorites(it, true) }
            }
        }
    }

    override suspend fun getFavoriteCurrenciesList(query: String, list: String) {
        val response = apiService.getFavoriteCurrenciesInfo(baseCurrency = query, currencies = list)
        Log.d("TESTREPO", response.toString())
        if (response.isSuccessful) {
            dao.insertFavoriteCurrencies(mapper.mapDtoToFavDbModel(response.body()!!))
        }
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