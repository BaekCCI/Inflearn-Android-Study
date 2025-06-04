package com.example.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.model.CurrentPrice
import com.example.coco.model.CurrentPriceResult
import com.example.coco.repository.NetworkRepository
import kotlinx.coroutines.launch

class SelectViewModel : ViewModel() {

    private val networkRepository = NetworkRepository()

    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    fun getCurrentCoinList() = viewModelScope.launch {
        val result = networkRepository.getCurrentCoinList()
        _currentPriceResult.value = result
    }

}