package com.borshevskiy.currencyexchangetestapp.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: CurrencyRepository) {

    suspend operator fun invoke() = repository.loadData()
}