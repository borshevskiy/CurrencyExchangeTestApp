package com.borshevskiy.currencyexchangetestapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencyList: List<CurrencyDbModel>)

    @Update
    suspend fun updateCurrency(currency: CurrencyDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDbModel)

    @Query("SELECT * FROM all_currencies_list")
    fun readCurrencies(): LiveData<List<CurrencyDbModel>>

    @Query("SELECT * FROM favorite_currencies_list")
    fun readFavoriteCurrencies(): LiveData<List<FavoriteCurrencyDbModel>>

    @Delete
    suspend fun deleteFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDbModel)

}