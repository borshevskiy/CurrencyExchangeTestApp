package com.borshevskiy.currencyexchangetestapp.domain

import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend operator fun invoke(currency: Currency) = repository.removeFromFavorites(currency)

}