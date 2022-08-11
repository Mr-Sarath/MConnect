package com.app.mconnect.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.app.mconnect.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        binding?.btLogin?.setOnClickListener {
            val email = binding?.etEmail?.text.toString()
            val password = binding?.etPassword?.text.toString()
            if (validation(email, password)) {
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
            }
        }

    }

    private fun validation(email: String, password: String): Boolean {
        if (password.isEmpty()) {
            Toast.makeText(this@LoginActivity, "password empty", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if ((!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            Toast.makeText(this@LoginActivity, "email empty", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true

    }
}