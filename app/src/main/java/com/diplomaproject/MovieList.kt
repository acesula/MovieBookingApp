package com.diplomaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.diplomaproject.databinding.ActivityMovieListBinding
import com.diplomaproject.movies.adapter.MyAdapter
import com.diplomaproject.movies.model.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MovieList : AppCompatActivity() {

    lateinit var binding: ActivityMovieListBinding
    lateinit var adapter: MyAdapter
    lateinit var databaseReference: DatabaseReference
    lateinit var movieList: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        databaseReference = FirebaseDatabase.getInstance().getReference("movies")
        movieList = arrayListOf<Movie>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    movieList.add(Movie(
                        postSnapshot.child("backdrop_path").value.toString(),
                        postSnapshot.child("original_language").value.toString(),
                        postSnapshot.child("overview").value.toString(),
                        postSnapshot.child("poster_path").value.toString(),
                        postSnapshot.child("release_date").value.toString(),
                        postSnapshot.child("title").value.toString(),
                        postSnapshot.child("vote_average").value.toString()
                    ))
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

}