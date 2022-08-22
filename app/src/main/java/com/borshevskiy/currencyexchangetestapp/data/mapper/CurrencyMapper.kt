package com.borshevskiy.currencyexchangetestapp.data.mapper

import com.borshevskiy.currencyexchangetestapp.data.database.CurrencyDbModel
import com.borshevskiy.currencyexchangetestapp.data.database.FavoriteCurrencyDbModel
import com.borshevskiy.currencyexchangetestapp.data.network.model.CurrencyInfoDto
import com.borshevskiy.currencyexchangetestapp.domain.Currency
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: CurrencyInfoDto): List<CurrencyDbModel> {
        val currencyList = mutableListOf<CurrencyDbModel>()
        dto.data.toString().split("),").forEach { currency ->
            currencyList.add(
                CurrencyDbModel(
                    currency.substringAfter("code=").substringBefore(","),
                    currency.substringAfter("value=").removeSuffix("))")
                )
            )
        }
        return currencyList
    }

    fun mapDbModelToCurrency(dbModel: CurrencyDbModel): Currency = Currency(dbModel.name, dbModel.value)

    fun mapFavDbModelToCurrency(favDbModel: FavoriteCurrencyDbModel): Currency = Currency(favDbModel.name, favDbModel.value)

    fun mapCurrencyToFavDbModel(currency: Currency): FavoriteCurrencyDbModel = FavoriteCurrencyDbModel(currency.name, currency.value)

    fun mapCurrencyToDbModel(currency: Currency): CurrencyDbModel = CurrencyDbModel(currency.name, currency.value, !currency.isFavorite)
}