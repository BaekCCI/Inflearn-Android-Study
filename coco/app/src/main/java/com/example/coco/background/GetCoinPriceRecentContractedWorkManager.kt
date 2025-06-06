package com.example.coco.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coco.db.entity.SelectedCoinPriceEntity
import com.example.coco.network.model.RecentCoinPriceList
import com.example.coco.repository.DBRepository
import com.example.coco.repository.NetworkRepository
import timber.log.Timber
import java.util.Calendar
import java.util.Date

//최근 거래된 코인 가격 내역을 가져오는 WorkManager
class GetCoinPriceRecentContractedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()

    private val networkRepository = NetworkRepository()

    val timeStamp = Calendar.getInstance().time

    override suspend fun doWork(): Result {
        Timber.d("doWork")

        getAllInterestSelectedCoinData()

        return Result.success()
    }

    suspend fun getAllInterestSelectedCoinData() {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        for (coinData in selectedCoinList) {
            val recentCoinPriceList = networkRepository.getInterestCoinPriceData(coinData.coin_name)
            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPriceList,
                timeStamp
            )
        }

    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentCoinPriceList,
        timeStamp: Date
    ) {
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp,
        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }


}