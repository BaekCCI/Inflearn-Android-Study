package com.example.coco.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.model.UpDownDataSet

class PriceListUpDownRVAdapter(val context: Context, val dataSet: List<UpDownDataSet>) :
    RecyclerView.Adapter<PriceListUpDownRVAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName = view.findViewById<TextView>(R.id.coinName)
        val coinPriceUpDown = view.findViewById<TextView>(R.id.coinPriceUpDown)
        val price = view.findViewById<TextView>(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_price_change_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = dataSet[position]
        holder.coinName.text = item.coinName

        if (item.upDownPrice.contains("-")) {
            holder.coinPriceUpDown.text = "하락"
            holder.coinPriceUpDown.setTextColor("#114fed".toColorInt())
        } else {
            holder.coinPriceUpDown.text = "상승"
            holder.coinPriceUpDown.setTextColor("#ed2e11".toColorInt())
        }

        holder.price.text = item.upDownPrice.split(".")[0]
    }
}