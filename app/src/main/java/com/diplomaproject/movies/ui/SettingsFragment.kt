package com.diplomaproject.movies.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.diplomaproject.activities.DeleteAccount
import com.diplomaproject.activities.ProfileActivity
import com.diplomaproject.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.editProfile.setOnClickListener(){
            val intent: Intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener(){
            val intent: Intent = Intent(requireContext(), DeleteAccount::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener() {
            Firebase.auth.signOut()
            goToLoginPage()
        }

        return binding.root
    }

    private fun fetchUserData() {
        showLoadingState()
        firestore.collection("Users")
            .whereEqualTo("uid", currentUser.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val userDocument = querySnapshot.documents[0]
                    val user = userDocument.toObject(User::class.java)
                    binding.user = user
                    if(user?.profile_picture != "") {
                        user?.profile_picture.let {
                            Glide.with(this)
                                .load(it)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.appCompatImageView)
                        }
                    }
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
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState() {
        binding.progressBar.visibility = View.GONE
    }


    private fun goToLoginPage() {
        val intent: Intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
   
}