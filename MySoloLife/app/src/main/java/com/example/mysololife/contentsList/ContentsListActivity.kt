package com.example.mysololife.contentsList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentsListActivity : AppCompatActivity() {

    lateinit var myRef: DatabaseReference
    val bookmarkIdList = mutableSetOf<String>()
    lateinit var rvAdapter: ContentsRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()
        rvAdapter = ContentsRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        val database = Firebase.database
        val category = intent.getStringExtra("category") ?: "category1"
        myRef = database.getReference(category)


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)


        val rv: RecyclerView = findViewById(R.id.rv)

        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()

    }

    private fun getBookmarkData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()
                for (dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ContentsListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }
}