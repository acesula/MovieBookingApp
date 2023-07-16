package com.diplomaproject.movies.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.diplomaproject.R
import com.diplomaproject.activities.SeatingActivity
import com.diplomaproject.databinding.FragmentMovieDetailsBinding
import com.diplomaproject.movies.model.Movie

class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding
    lateinit var movieName: String
    lateinit var moviePoster: String
    lateinit var room: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        val movie = arguments?.getParcelable<Movie>("movie")
        movie?.let {
            binding.movie = it
            movieName = it.title.toString()
            moviePoster = it.poster_path.toString()
            room = it.room.toString()
        }

        binding.bookTicket.setOnClickListener() {
            val intent: Intent = Intent(requireContext(), SeatingActivity::class.java)
            intent.putExtra("movie_name", movieName)
            intent.putExtra("movie_poster", moviePoster)
            intent.putExtra("room", room)
            startActivity(intent)
        }
        return binding.root
    }

}