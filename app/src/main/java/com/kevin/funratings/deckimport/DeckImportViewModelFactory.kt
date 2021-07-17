package com.kevin.funratings.deckimport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevin.funratings.detail.CardFragmentViewModel
import com.kevin.funratings.repository.CardRepository
import com.kevin.funratings.room.RatingsRepository

class DeckImportViewModelFactory(private val cardRepository: CardRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeckImportViewModel(cardRepository) as T
    }
}