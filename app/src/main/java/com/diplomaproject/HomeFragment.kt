package com.diplomaproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.diplomaproject.authentication.Login
import com.diplomaproject.databinding.FragmentHomeBinding
import com.diplomaproject.movies.adapter.MyAdapter
import com.diplomaproject.movies.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var auth: FirebaseAuth
    lateinit var adapter: MyAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var movieList: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        moviesOnTheatre()
        return binding.root
    }

    private fun moviesOnTheatre() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        databaseReference = FirebaseDatabase.getInstance().getReference("movies")
        movieList = arrayListOf<Movie>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    movieList.add(
                        Movie(
                            postSnapshot.child("backdrop_path").value.toString(),
                            postSnapshot.child("original_language").value.toString(),
                            postSnapshot.child("overview").value.toString(),
                            postSnapshot.child("poster_path").value.toString(),
                            postSnapshot.child("release_date").value.toString(),
                            postSnapshot.child("title").value.toString(),
                            postSnapshot.child("vote_average").value.toString()
                        )
                    )
                }
                adapter = MyAdapter(binding.root.context, movieList)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser

        if (user == null) {
            goToLoginPage()
        }

    }

    private fun goToLoginPage() {
        val intent: Intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


}