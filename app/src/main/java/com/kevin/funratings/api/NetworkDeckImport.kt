package com.kevin.funratings.api

data class NetworkDeckImport(
    val data: NetworkInfo
) {
    data class NetworkInfo (
        val text: String,
        val clan: String,
        val hash: String,
        val errors: List<DeckCodeError>
            )
    data class DeckCodeError (
        val type: String,
        val message: String
            )
}
