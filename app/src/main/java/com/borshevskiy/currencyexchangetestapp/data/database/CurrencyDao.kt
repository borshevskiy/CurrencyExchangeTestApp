package com.borshevskiy.currencyexchangetestapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencyList: List<CurrencyDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: CurrencyDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCurrencies(favoriteCurrency: FavoriteCurrencyDbModel)

    @Query("SELECT * FROM all_currencies_list")
    fun readCurrencies(): LiveData<List<CurrencyDbModel>>

    @Query("SELECT * FROM favorite_currencies_list")
    fun readFavoriteCurrencies(): LiveData<List<FavoriteCurrencyDbModel>>

    @Delete
    suspend fun deleteFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDbModel)

}