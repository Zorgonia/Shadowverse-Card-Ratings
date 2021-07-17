package com.kevin.funratings.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.funratings.api.CardService
import com.kevin.funratings.model.CardDetail
import com.kevin.funratings.room.Ratings
import com.kevin.funratings.room.RatingsRepository
import kotlinx.coroutines.launch
import java.util.*

class CardFragmentViewModel(private val id: String, private val locale: String, private val clanNum: String,
                            private val ratingsRepository: RatingsRepository): ViewModel() {

    val allRatings = ratingsRepository.allRatings

    private val cardData: MutableLiveData<String> = MutableLiveData()
    fun cardData(): LiveData<String> = cardData

    private val cardRating = ratingsRepository.getRating(id)
    fun cardRating(): LiveData<List<Ratings>> = cardRating

    private var thisLocale = locale

    var parsedData:CardDetail? = null

    private val state: MutableLiveData<CardDetailState> = MutableLiveData(CardDetailState.NotEvoState)
    fun state() : LiveData<CardDetailState> = state

    init {
        viewModelScope.launch {
            fetchCardData()
        }
    }

    private suspend fun fetchCardData() {
        cardData.postValue(CardService.cardRepository.getCardData(id, thisLocale) ?: "xd")
    }

    fun formatSidebar(): String {
        return "${parsedData?.sidebarDetail?.clan} ${parsedData?.sidebarDetail?.cv} ${parsedData?.sidebarDetail?.cardpack}"
    }

    fun insert(rating: Int) = viewModelScope.launch {
        parsedData?.let {
            ratingsRepository.insert(Ratings(id, it.name, rating, clanNum))
        }
    }

    fun changeShowEvo(notAFollower: Boolean) {
        Log.d("hello", "${state.value} $notAFollower hi")
        if (notAFollower) return
        state.postValue(if (state.value == CardDetailState.NotEvoState) CardDetailState.EvoState else CardDetailState.NotEvoState)
    }
}