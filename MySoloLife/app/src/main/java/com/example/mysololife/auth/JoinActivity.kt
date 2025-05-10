package com.example.mysololife.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mysololife.MainActivity
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        auth = Firebase.auth

        binding.joinBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val pw1 = binding.pwArea1.text.toString()
            val pw2 = binding.pwArea2.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
            } else if (pw1.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            } else if (pw2.isEmpty()) {
                Toast.makeText(this, "비밀번호 확인을 입력해주세요", Toast.LENGTH_LONG).show()
            } else if (!pw1.equals(pw2)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            } else if (pw1.length < 6) {
                Toast.makeText(this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, pw1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }
}