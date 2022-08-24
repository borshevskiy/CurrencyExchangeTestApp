package com.borshevskiy.currencyexchangetestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borshevskiy.currencyexchangetestapp.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    readAndFilterCurrenciesUseCase: ReadAndFilterCurrenciesUseCase,
    readAndFilterFavCurrenciesUseCase: ReadAndFilterFavCurrenciesUseCase,
    private val getAllCurrenciesListUseCase: GetAllCurrenciesListUseCase,
    private val getFavoriteCurrenciesListUseCase: GetFavoriteCurrenciesListUseCase,
    private val saveAndRemoveFromFavoritesUseCase: SaveAndRemoveFromFavoritesUseCase

) : ViewModel() {

    lateinit var filter: String

    /** ROOM DATABASE **/
    val readCurrencies = readAndFilterCurrenciesUseCase(filter)
    val readFavoriteCurrencies = readAndFilterFavCurrenciesUseCase()

    fun insertOrRemoveFromFavorites(currency: Currency) = viewModelScope.launch {
        saveAndRemoveFromFavoritesUseCase(currency)
    }

    /** RETROFIT **/
    fun getCurrencies(query: String) = viewModelScope.launch {
        getAllCurrenciesListUseCase(query)
    }

    fun getFavoriteCurrencies(query: String, list: String) = viewModelScope.launch {
        getFavoriteCurrenciesListUseCase(query, list)
    }
}