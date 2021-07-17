package com.kevin.funratings.repository

import android.util.Log
import com.kevin.funratings.api.CardApi
import com.kevin.funratings.api.NetworkDeckImport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteCardRepository(private val api: CardApi): CardRepository {
    private val dispatcher = Dispatchers.IO


    override suspend fun getHomepage(clan: String, locale: String): String? = withContext(dispatcher) {
        val res = api.getHomepage(clan, locale)
        if (res.isSuccessful) {
            res.body()
        } else {
            Log.e("hahah", "${res.errorBody()}")
            "failure"
        }
    }

    override suspend fun getCardData(id: String, locale: String): String? = withContext(dispatcher){
        val res = api.getCard(id, locale)
        if (res.isSuccessful) {
            res.body()
        } else {
            Log.e("hahah get rekt kiddo", "${res.errorBody()}")
            "failure"
        }
    }

    override suspend fun getDeckLink(deckcode: String): NetworkDeckImport? = withContext(dispatcher){
        val res = api.getDeckLink("json", deckcode)
        if (res.isSuccessful) {
            res.body()
        } else {
            Log.e("something went wrong", "${res.errorBody()}")
            null
        }
    }

}