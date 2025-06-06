package com.example.coco.repository

import com.example.coco.model.CurrentPrice
import com.example.coco.model.CurrentPriceResult
import com.example.coco.network.RetrofitInstance
import com.google.gson.Gson
import timber.log.Timber

class NetworkRepository {

    suspend fun getCurrentCoinList(): List<CurrentPriceResult> {
        val result = RetrofitInstance.api.getCurrentCoinList()

        val currentPriceList = ArrayList<CurrentPriceResult>()
        val gson = Gson()

        for (coin in result.data) {
            if (coin.key == "date") continue
            try {
                val gsonToJson = gson.toJson(coin.value)
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                currentPriceList.add(currentPriceResult)
            } catch (e: java.lang.Exception) {
                Timber.e(e.toString())
            }
        }
        return currentPriceList
    }

    suspend fun getInterestCoinPriceData(coin: String) =
        RetrofitInstance.api.getRecentCoinPrice(coin)
}