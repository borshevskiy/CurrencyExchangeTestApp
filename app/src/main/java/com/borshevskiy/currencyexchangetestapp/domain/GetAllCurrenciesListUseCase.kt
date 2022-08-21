package com.borshevskiy.currencyexchangetestapp.domain

import javax.inject.Inject

class GetAllCurrenciesListUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke() = repository.getAllCurrenciesList()

}