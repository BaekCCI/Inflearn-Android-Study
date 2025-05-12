package com.example.mysololife.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityBoardInsideBinding
import com.example.mysololife.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName
    private lateinit var binding: ActivityBoardInsideBinding

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        //두번째 방법
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)
    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle("게시글 수정/삭제")
        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)

        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show()
        }
    }

    private fun getImageData(key: String) {
        val storageReference = Firebase.storage.reference.child(key + ".png")

        val imageView = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this).load(task.result).into(imageView)
            } else {

            }
        })
    }

    private fun getBoardData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataModel = dataSnapshot.getValue(BoardModel::class.java)
                if (dataModel != null) {
                    binding.titleArea.text = dataModel.title
                    binding.contentArea.text = dataModel.content
                    binding.timeArea.text = dataModel.time
                } else {
                    finish()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}