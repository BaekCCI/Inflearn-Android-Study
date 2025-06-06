package com.example.coco.repository

import com.example.coco.App
import com.example.coco.db.CoinPriceDatabase
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.db.entity.SelectedCoinPriceEntity

class DBRepository {
    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    fun getAllInterestCoinData() = db.interestCoinDao().getAllData()

    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDao().insert(interestCoinEntity)

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDao().update(interestCoinEntity)

    fun getAllInterestSelectedCoinData() = db.interestCoinDao().getSelectedData()

    //coinPrice
    fun getAllCoinPriceData() = db.selectedCoinDao().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) =
        db.selectedCoinDao().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName: String) = db.selectedCoinDao().getOneCoinData(coinName)
}