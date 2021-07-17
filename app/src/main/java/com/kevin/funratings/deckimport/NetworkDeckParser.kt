package com.kevin.funratings.deckimport

import com.kevin.funratings.api.NetworkDeckImport

object NetworkDeckParser {

    var locale = ""

    fun getDeckLink(data: NetworkDeckImport): String {
        return "https://shadowverse-portal.com/deck/${data.data.hash}?lang=${locale}"
    }

    fun getDeckBuilderLink(data: NetworkDeckImport): String {
        return "https://shadowverse-portal.com/deckbuilder/create/${data.data.clan}?hash=${data.data.hash}&lang=${locale}"
    }

    fun dataIsValid(data: NetworkDeckImport?): Boolean {
        return data?.data?.errors?.isEmpty() ?: false
    }
}