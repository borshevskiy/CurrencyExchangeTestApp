package com.borshevskiy.currencyexchangetestapp.domain

import javax.inject.Inject

class SaveToFavoritesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend operator fun invoke(currency: Currency) = repository.saveToFavorites(currency)
}