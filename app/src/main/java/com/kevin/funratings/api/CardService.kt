package com.kevin.funratings.api

import com.kevin.funratings.repository.CardRepository
import com.kevin.funratings.repository.RemoteCardRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object CardService {

    private const val BASE_URL = "https://shadowverse-portal.com"

    private var api: CardApi

    var cardRepository : CardRepository

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(CardApi::class.java)
        cardRepository = RemoteCardRepository(api)
    }

}