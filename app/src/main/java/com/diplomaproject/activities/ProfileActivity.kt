package com.diplomaproject.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.diplomaproject.R
import com.diplomaproject.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var user: FirebaseUser
    private lateinit var imageUri: Uri
    var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    lateinit var imageUrl: String

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            if (imageUri != null) {
                this.imageUri = imageUri
                binding.postImageView.setImageURI(imageUri)

                // Convert image URI to HTTPS URL
                val storageReference = FirebaseStorage.getInstance().reference
                val filePath: StorageReference = storageReference.child("profile_pictures")
                val fileRef = filePath.child(imageUri.lastPathSegment!!)
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    imageUrl= uri.toString()
                }.addOnFailureListener { exception ->
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        user = FirebaseAuth.getInstance().currentUser!!
        binding.postCameraButton.setOnClickListener() {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        binding.button.setOnClickListener() {
            updateProfile()
        }
    }

    private fun validateUser(
        name: String,
        surname: String,
        email: String,
        password: String,
        confirmPass: String
    ) {
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
//                val profileUpdates = userProfileChangeRequest{
//                    displayName = "$name $surname"
//                    photoUri = Uri.parse("https://diploma.com/${imageUri.toString()}")
//                }
//                user!!.updateProfile(profileUpdates)
//                val credential = EmailAuthProvider
//                    .getCredential(user.email!!)
//                user.reauthenticate(credential)

                val userDocRef = database.collection("Users").document(user.uid)

                val updatedData = hashMapOf<String, Any>(
                    "uid" to user.uid.toString(),
                    "name" to name,
                    "surname" to surname,
                    "email" to email,
                    "profile_picture" to imageUri.toString()
                )
                userDocRef.set(updatedData)
//                user!!.updateEmail(email)
//                user!!.updatePassword(password)
                finish()
            } else {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProfile() {
        val name = binding.name.text.toString()
        val surname = binding.surname.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPass = binding.confirmPassword.text.toString()

        validateUser(name,surname,email,password,confirmPass)
    }



}