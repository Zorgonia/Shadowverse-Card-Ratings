package com.kevin.funratings.repository

import com.kevin.funratings.api.NetworkDeckImport

interface CardRepository {
    suspend fun getHomepage(clan:String, locale: String): String?
    suspend fun getCardData(id: String, locale:String): String?
    suspend fun getDeckLink(deckcode: String): NetworkDeckImport?
}