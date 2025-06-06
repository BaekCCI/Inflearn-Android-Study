package com.example.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataStore.MyDataStore
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.model.CurrentPrice
import com.example.coco.model.CurrentPriceResult
import com.example.coco.repository.DBRepository
import com.example.coco.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectViewModel : ViewModel() {

    private val networkRepository = NetworkRepository()
    private val dbRepository = DBRepository()

    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    fun getCurrentCoinList() = viewModelScope.launch {
        val result = networkRepository.getCurrentCoinList()
        currentPriceResultList = ArrayList(result)
        _currentPriceResult.value = result
    }

    private val _saved = MutableLiveData<String>()
    val saved: LiveData<String> get() = _saved

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setUpFirstData()
    }

    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =
        viewModelScope.launch(Dispatchers.IO) {
            //1. 전체 코인 데이터를 가져와서
            for (coin in currentPriceResultList) {

                val selected = selectedCoinList.contains(coin.coinName)

                val interestCoinEntity = InterestCoinEntity(
                    0,
                    coin.coinName,
                    coin.coinInfo.opening_price,
                    coin.coinInfo.closing_price,
                    coin.coinInfo.min_price,
                    coin.coinInfo.max_price,
                    coin.coinInfo.units_traded,
                    coin.coinInfo.acc_trade_value,
                    coin.coinInfo.prev_closing_price,
                    coin.coinInfo.units_traded_24H,
                    coin.coinInfo.acc_trade_value_24H,
                    coin.coinInfo.fluctate_24H,
                    coin.coinInfo.fluctate_rate_24H,
                    selected,
                )

                interestCoinEntity.let {
                    dbRepository.insertInterestCoinData(it)
                }
            }

            withContext(Dispatchers.Main) {
                _saved.value = "done"
            }
        }

}