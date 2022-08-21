package com.borshevskiy.currencyexchangetestapp.domain

import javax.inject.Inject

class GetFavoriteCurrenciesListUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke() = repository.getFavoriteCurrenciesList()

}