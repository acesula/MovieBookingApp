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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.diplomaproject.R
import com.diplomaproject.authentication.data.User
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

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data
                if (imageUri != null) {
                    this.imageUri = imageUri
                    binding.postImageView.setImageURI(imageUri)

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
        var nameNew = name
        var surnameNew = surname
        var emailNew = email
        val userDocRef = database.collection("Users").document(user.uid)
        database.collection("Users")
            .whereEqualTo("uid", user.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val userDocument = querySnapshot.documents[0]
                    val userData = userDocument.toObject(User::class.java)

                    if (TextUtils.isEmpty(nameNew)) {
                        nameNew = userData?.name.toString()
                    }
                    if (TextUtils.isEmpty(surnameNew)) {
                        surnameNew = userData?.surname.toString()
                    }
                    if (TextUtils.isEmpty(emailNew)) {
                        emailNew = userData?.email.toString()
                    }
                    val updatedData = hashMapOf<String, Any>(
                        "uid" to user.uid,
                        "name" to nameNew,
                        "surname" to surnameNew,
                        "email" to emailNew,
                        "profile_picture" to imageUri.toString()
                    )
                    userDocRef.update(updatedData).addOnSuccessListener {
                        if(!TextUtils.isEmpty(email) && isValidEmail(email)){
                            user!!.updateEmail(email)
                        }
                        if (password.length > 3 && password == confirmPass) {
                            user!!.updatePassword(password)
                        }
                    }
                    finish()
                }
            }

    }

    private fun updateProfile() {
        val name = binding.name.text.toString()
        val surname = binding.surname.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPass = binding.confirmPassword.text.toString()

        validateUser(name, surname, email, password, confirmPass)
    }
}
