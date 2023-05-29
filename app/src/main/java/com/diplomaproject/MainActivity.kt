package com.diplomaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.diplomaproject.authentication.Login
import com.diplomaproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val user: FirebaseUser? = auth.currentUser

        if (user == null) {
            goToLoginPage()
        }
        else{
            binding.textView.text = user.email
        }

        binding.logoutBtn.setOnClickListener(){
            auth.signOut()
            goToLoginPage()
        }
    }

    private fun goToLoginPage() {
        val intent: Intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}