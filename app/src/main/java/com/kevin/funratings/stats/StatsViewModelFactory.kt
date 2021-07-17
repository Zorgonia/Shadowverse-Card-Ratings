package com.kevin.funratings.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevin.funratings.main.MainViewModel
import com.kevin.funratings.room.RatingsRepository

class StatsViewModelFactory(private val repo: RatingsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatsViewModel(repo) as T
    }
}