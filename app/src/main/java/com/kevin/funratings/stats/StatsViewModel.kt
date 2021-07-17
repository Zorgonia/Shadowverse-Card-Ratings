package com.kevin.funratings.stats

import androidx.lifecycle.ViewModel
import com.kevin.funratings.room.RatingsRepository

class StatsViewModel (private val ratingsRepository: RatingsRepository): ViewModel() {

    private val allRatings = ratingsRepository.allRatings
    fun allRatings() = allRatings

}