package com.kevin.funratings.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevin.funratings.room.RatingsRepository

class CardFragmentViewModelFactory(private val id: String, private val locale: String, private val clanNum: String,
                                   private val repo: RatingsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CardFragmentViewModel(id, locale, clanNum, repo) as T
    }
}