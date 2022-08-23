package com.borshevskiy.currencyexchangetestapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencyList: List<CurrencyDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCurrencies(currencyList: List<CurrencyDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDbModel)

    @Update
    suspend fun updateCurrency(currency: CurrencyDbModel)

    @Query("SELECT * FROM all_currencies_list ORDER BY name ASC")
    fun readCurrenciesNameAsc(): LiveData<List<CurrencyDbModel>>

    @Query("SELECT * FROM all_currencies_list ORDER BY name DESC")
    fun readCurrenciesNameDesc(): LiveData<List<CurrencyDbModel>>

    @Query("SELECT * FROM all_currencies_list ORDER BY value ASC")
    fun readCurrenciesValueAsc(): LiveData<List<CurrencyDbModel>>

    @Query("SELECT * FROM all_currencies_list ORDER BY value DESC")
    fun readCurrenciesValueDesc(): LiveData<List<CurrencyDbModel>>

    @Query("SELECT * FROM favorite_currencies_list ORDER BY name ASC")
    fun readFavoriteCurrenciesNameAsc(): LiveData<List<FavoriteCurrencyDbModel>>

    @Query("SELECT * FROM favorite_currencies_list ORDER BY name DESC")
    fun readFavoriteCurrenciesNameDesc(): LiveData<List<FavoriteCurrencyDbModel>>

    @Query("SELECT * FROM favorite_currencies_list ORDER BY value ASC")
    fun readFavoriteCurrenciesValueAsc(): LiveData<List<FavoriteCurrencyDbModel>>

    @Query("SELECT * FROM favorite_currencies_list ORDER BY value DESC")
    fun readFavoriteCurrenciesValueDesc(): LiveData<List<FavoriteCurrencyDbModel>>

    @Delete
    suspend fun deleteFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDbModel)

}