package com.kevin.funratings.home

import android.util.Log
import androidx.lifecycle.*
import com.kevin.funratings.api.CardService
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {

    private val homepageData: MutableLiveData<String> = MutableLiveData()
    fun hompageData(): LiveData<String> = homepageData

    private val updateData: MutableLiveData<String> = MutableLiveData()
    fun updateData(): LiveData<String> = updateData

    var hasLoaded = false

    private var locale: String = "ja"
    fun getLocale() = locale
    var localeNum: Int = 0
    private var clanVM: String = "0"
    var clanNum: Int = 0

    init {
        viewModelScope.launch {
            setUpData()
        }
    }

    private suspend fun setUpData() {
        homepageData.postValue(CardService.cardRepository.getHomepage(clanVM, locale) ?: "xd")
        //changeClanData(clanVM)
        updateRecyclerData()
        Log.d("inside setup data", "for viewmodel test")
    }

    fun hasLoaded() = hasLoaded


    fun changeClanData(clan: String, num: Int) {
        if (clanVM == clan) return
        clanNum = num
        clanVM = clan
        updateRecyclerData()
    }

    private fun updateRecyclerData() {
        viewModelScope.launch {
            updateData.postValue(CardService.cardRepository.getHomepage(clanVM, locale) ?: "xd")
        }
    }

    fun changeLocale(locale:String, num: Int) {
        if (this.locale == locale) return
        localeNum = num
        this.locale = locale
        updateRecyclerData()
    }

}