package com.kevin.funratings.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.funratings.room.RatingsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repo: RatingsRepository): ViewModel() {

    var currentFragmentTag = ""

    val locale: MutableLiveData<String> = MutableLiveData("ja")

    fun deleteAllEntries() {
        viewModelScope.launch {
            repo.deleteAll()
        }
    }
}
