package com.kevin.funratings.model

data class CardEffect(
    var effect: String,
    var description: String,
    var evoText: String = "",
    var attack: String = "",
    var hp: String = ""
) {
}