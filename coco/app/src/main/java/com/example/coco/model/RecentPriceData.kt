package com.example.coco.model

data class RecentPriceData(
    val transaction_date: String,
    val type: String,
    val units_traded: String,
    val price: String,
    val total: String
)
