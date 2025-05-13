package com.example.mysololife.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object {
        private val database = Firebase.database
        val category1 = database.getReference("category1")
        val category2 = database.getReference("category2")
        val bookmarkRef = database.getReference("bookmark_list")
        val boardRef = database.getReference("board")
        val commentRef = database.getReference("comment")
    }

}