package com.example.mysololife.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef

class ContentsRVAdapter(
    val context: Context,
    val items: ArrayList<ContentModel>,
    val keyList: ArrayList<String>,
    val bookmarkIdList: MutableSet<String>
) :
    RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) {

        holder.bindItems(items[position], keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentModel, key: String) {

            itemView.setOnClickListener {
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url", item.webUrl)
                itemView.context.startActivity(intent)
            }
            //itemView == 20라인 content_rcv_item
            val contentTitle = itemView.findViewById<TextView>(R.id.txtArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imgArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if (bookmarkIdList.contains(key)) {
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            bookmarkArea.setOnClickListener {
                if (bookmarkIdList.contains(key)) {
                    bookmarkIdList.remove(key)
                    FBRef.bookmarkRef.child(FBAuth.getUid()).child(key).removeValue()
                } else {
                    FBRef.bookmarkRef.child(FBAuth.getUid()).child(key)
                        .setValue(BookmarkModel(true))
                }
            }
            contentTitle.text = item.title

            Glide.with(context).load(item.imageUrl).into(imageViewArea)


        }
    }

}