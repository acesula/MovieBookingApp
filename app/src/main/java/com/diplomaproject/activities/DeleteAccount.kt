package com.diplomaproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.diplomaproject.R
import com.diplomaproject.authentication.Login
import com.diplomaproject.databinding.ActivityDeleteAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DeleteAccount : AppCompatActivity() {

    lateinit var binding: ActivityDeleteAccountBinding
    lateinit var user: FirebaseUser
    lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_account)
        user = Firebase.auth.currentUser!!
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        binding.delBtn.setOnClickListener() {
            deleteUser()
        }
    }

    private fun deleteUser() {
        val password = binding.password.text.toString()
        val confirmPass = binding.confirmPassword.text.toString()

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass) ||
            password.length < 4 || confirmPass.length < 4
        ) {
            if (TextUtils.isEmpty(password) && password.length < 4) {
                binding.password.error = "Password length is less than 4 characters!"
            }
            if (TextUtils.isEmpty(confirmPass) && confirmPass.length < 4) {
                binding.confirmPassword.error = "Password length is less than 4 characters!"
            }
        } else {
            if (confirmPass == password) {
                auth.signInWithEmailAndPassword(auth.currentUser?.email.toString(), password)
                    .addOnCompleteListener(this) {
                        firestore.collection("Users")
                            .document(auth.currentUser?.uid.toString())
                            .delete().addOnSuccessListener {
                                user.delete()
                                auth.signOut()
                                goToLoginPage()
                            }
                    }.addOnFailureListener(this) {
                    Toast.makeText(this, "Password not valid!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToLoginPage() {
        val intent: Intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}