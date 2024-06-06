package com.example.gamerockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamerockpaperscissors.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.signupButton.setOnClickListener {
            val signupUserName = binding.signupEmail.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            signupDatabase(signupUserName, signupPassword)
        }

        binding.loginRedirectText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signupDatabase(username: String, password: String) {
        val insertedRowId = databaseHelper.insertUser(username, password)
        if (insertedRowId != -1L) {
            Toast.makeText(this, "Вы зарегистрировались!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Ошибка регистрации!", Toast.LENGTH_SHORT).show()
        }

    }

}