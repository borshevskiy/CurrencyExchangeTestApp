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
    private val saveAndRemoveFromFavoritesUseCase: SaveAndRemoveFromFavoritesUseCase
) : ViewModel() {

    val allCurrenciesList = getAllCurrenciesListUseCase()
    val favoriteCurrenciesList = getFavoriteCurrenciesListUseCase()

    fun insertOrRemoveFromFavorites(currency: Currency) = viewModelScope.launch {
        saveAndRemoveFromFavoritesUseCase(currency)
    }

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}