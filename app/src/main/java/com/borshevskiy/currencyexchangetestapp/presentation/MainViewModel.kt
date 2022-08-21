package com.borshevskiy.currencyexchangetestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borshevskiy.currencyexchangetestapp.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadDataUseCase: LoadDataUseCase,
    private val getAllCurrenciesListUseCase: GetAllCurrenciesListUseCase,
    private val getFavoriteCurrenciesListUseCase: GetFavoriteCurrenciesListUseCase,
    private val saveToFavoritesUseCase: SaveToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    val allCurrenciesList = getAllCurrenciesListUseCase()
    val favoriteCurrenciesList = getFavoriteCurrenciesListUseCase()

    fun insertToFavorites(currency: Currency) = viewModelScope.launch {
        saveToFavoritesUseCase(currency)
    }

    fun deleteFromFavorites(currency: Currency) = viewModelScope.launch {
        removeFromFavoritesUseCase(currency)
    }

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}