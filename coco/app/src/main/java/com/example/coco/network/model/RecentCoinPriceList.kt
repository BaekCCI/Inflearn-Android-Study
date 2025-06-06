package com.example.coco.network.model

import com.example.coco.model.RecentPriceData

data class RecentCoinPriceList(

    val status: String,
    val data: List<RecentPriceData>
)