package com.diplomaproject.authentication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.diplomaproject.MainActivity
import com.diplomaproject.R
import com.diplomaproject.authentication.data.User
import com.diplomaproject.databinding.ActivityRegisterBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.Date


class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    var collectionReference: CollectionReference = database.collection("Users")
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    private fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun goToLoginPage() {
        val intent: Intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        auth = FirebaseAuth.getInstance()


        binding.loginNow.setOnClickListener() {
            goToLoginPage()
        }
        binding.registerBtn.setOnClickListener() {
            registerUser()
        }
    }

    private fun registerUser() {
        binding.progressBar.visibility = View.VISIBLE
        val name = binding.name.text.toString()
        val surname = binding.surname.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPass = binding.confirmPassword.text.toString()

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) ||
            TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
            TextUtils.isEmpty(confirmPass) || !isValidEmail(email) ||
            password.length < 4 || confirmPass.length < 4
        ) {
            if (TextUtils.isEmpty(name)) {
                binding.name.error = "Enter name!"
            }
            if (TextUtils.isEmpty(surname)) {
                binding.surname.error = "Enter surname!"
            }
            if (!isValidEmail(email)) {
                binding.email.error = "Enter valid email!"
            }
            if (TextUtils.isEmpty(password) && password.length < 4) {
                binding.password.error = "Password length is less than 4 characters!"
            }
            if (TextUtils.isEmpty(confirmPass) && confirmPass.length < 4) {
                binding.confirmPassword.error = "Password length is less than 4 characters!"
            }
            binding.progressBar.visibility = View.GONE
        } else {
            if (confirmPass == password) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            val user = auth.currentUser
//                            val profileUpdates = userProfileChangeRequest{
//                                displayName = "$name $surname"
//                                photoUri = Uri.parse("")
//                            }
//                            user!!.updateProfile(profileUpdates)
                            saveToDb(name, surname, email, user)
                            goToLoginPage()
                        } else {
                            Toast.makeText(
                                this,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToDb(name: String, surname: String, email: String, user: FirebaseUser?) {
        val image = "";
        val user1: User = User(name, surname, email, user?.uid.toString(),image)
        database = Firebase.firestore
        val userDocRef = database.collection("Users").document(user?.uid.toString())
        userDocRef.set(user1)

    }
}

