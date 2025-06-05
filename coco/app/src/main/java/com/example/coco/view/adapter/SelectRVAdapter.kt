package com.example.coco.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.model.CurrentPriceResult
import com.example.coco.network.model.CurrentPriceList
import androidx.core.graphics.toColorInt

class SelectRVAdapter(val context: Context, val coinPriceList: List<CurrentPriceResult>) :
    RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {

    val selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val coinName: TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown: TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImage: ImageView = view.findViewById(R.id.likeBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = coinPriceList[position]

        holder.coinName.text = item.coinName

        val fluctate_24H = item.coinInfo.fluctate_24H
        if (fluctate_24H.contains("-")) {
            holder.coinPriceUpDown.text = "하락입니다."
            holder.coinPriceUpDown.setTextColor("#114fed".toColorInt())
        } else {
            holder.coinPriceUpDown.text = "상승입니다."
            holder.coinPriceUpDown.setTextColor("#ed2e11".toColorInt())
        }

        val likeImage = holder.likeImage

        if(selectedCoinList.contains(item.coinName)){
            likeImage.setImageResource(R.drawable.like_red)
        }else{
            likeImage.setImageResource(R.drawable.like_grey)
        }

        likeImage.setOnClickListener {
            if (selectedCoinList.contains(item.coinName)) {
                selectedCoinList.remove(item.coinName)
                likeImage.setImageResource(R.drawable.like_grey)
            } else {
                selectedCoinList.add(item.coinName)
                likeImage.setImageResource(R.drawable.like_red)
            }
        }

    }
}