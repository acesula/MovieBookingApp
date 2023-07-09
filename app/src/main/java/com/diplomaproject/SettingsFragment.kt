package com.diplomaproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.diplomaproject.authentication.Login
import com.diplomaproject.authentication.data.User
import com.diplomaproject.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private var cachedUserData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.editProfile.setOnClickListener(){
            val intent: Intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener() {
            Firebase.auth.signOut()
            goToLoginPage()
        }

        return binding.root
    }

    private fun fetchUserData() {
        // Check if the user data is already cached
        if (cachedUserData != null) {
            binding.user = cachedUserData
            return
        }

//        showLoadingState()
//
//        val userAuth = Firebase.auth.currentUser
//        val name = userAuth?.displayName
//        val email = userAuth?.email
//        val photoUrl = userAuth?.photoUrl.toString()
//
//
//        if(photoUrl != null){
//            photoUrl?.let {
//                Glide.with(this)
//                    .load(it)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(binding.profilePicture)
//            }
//        }
//        val user: User = User(name,"",email,userAuth?.uid.toString(),photoUrl.toString())
//        binding.user = user
//
//        hideLoadingState()
        firestore.collection("Users")
            .whereEqualTo("uid", currentUser.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val userDocument = querySnapshot.documents[0]
                    val user = userDocument.toObject(User::class.java)
                    cachedUserData = user
                    binding.user = user
//                    if(user?.profile_picture != null) {
//                        user.profile_picture.let {
//                            Glide.with(this)
//                                .load(it)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(binding.appCompatImageView)
//                        }
//                    }
                } else {
                    Log.d("SettingsFragment", "No matching documents found")
                }
                hideLoadingState()
            }
            .addOnFailureListener { exception ->
                Log.e("SettingsFragment", "Error fetching user data: $exception")
                hideLoadingState()
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUserData()
    }
    private fun showLoadingState() {
        // Show a loading state indicator (e.g., progress bar)
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState() {
        // Hide the loading state indicator (e.g., progress bar)
        binding.progressBar.visibility = View.GONE
    }


    private fun goToLoginPage() {
        val intent: Intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
   
}