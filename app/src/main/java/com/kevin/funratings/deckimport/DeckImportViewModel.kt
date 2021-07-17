package com.kevin.funratings.deckimport

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.funratings.api.CardService
import com.kevin.funratings.api.NetworkDeckImport
import com.kevin.funratings.repository.CardRepository
import kotlinx.coroutines.launch

class DeckImportViewModel(private val cardRepository: CardRepository) : ViewModel(){

    private val deckImportData: MutableLiveData<NetworkDeckImport?> = MutableLiveData()
    fun deckImportData(): LiveData<NetworkDeckImport?> = deckImportData

    var lastDeckCode = ""

    init {
        Log.d("hello", "im confirming im in here")
    }

    fun getDeckLink(deckcode: String)  {
        viewModelScope.launch {
            deckImportData.postValue(cardRepository.getDeckLink(deckcode))
        }
    }
}