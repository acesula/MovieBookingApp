package com.diplomaproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.diplomaproject.authentication.Login
import com.diplomaproject.databinding.FragmentSettingsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.logoutBtn.setOnClickListener(){
            Firebase.auth.signOut()
            goToLoginPage()
        }

        return binding.root
    }

    private fun goToLoginPage() {
        val intent: Intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}