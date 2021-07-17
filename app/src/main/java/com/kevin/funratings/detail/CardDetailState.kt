package com.kevin.funratings.detail

sealed class CardDetailState {
    object EvoState: CardDetailState()
    object NotEvoState: CardDetailState()
}
