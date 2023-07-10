package com.diplomaproject.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.diplomaproject.activities.MainActivity
import com.diplomaproject.R
import com.diplomaproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    private fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    private fun toMain() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            toMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        binding.registerNow.setOnClickListener() {
            val intent: Intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginBtn.setOnClickListener() {
            validateUser()
        }
    }

    private fun validateUser() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
            !isValidEmail(email) || password.length < 4) {
            if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                binding.email.error = "Enter valid email!"
            }
            if (TextUtils.isEmpty(password) || password.length < 4) {
                binding.password.error = "Password less then 4 characters!"
            }
        } else {
            binding.progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        toMain()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}