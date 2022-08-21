package com.borshevskiy.currencyexchangetestapp.data.network

import com.borshevskiy.currencyexchangetestapp.data.network.model.CurrencyInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v3/latest")
    suspend fun getCurrenciesInfo(
        @Query("apikey") apiKey: String = "Fml1dGeAfbmVOE0cZebLLYV2gBq1gZb1xfFBON37"
    ): Response<CurrencyInfoDto>
}