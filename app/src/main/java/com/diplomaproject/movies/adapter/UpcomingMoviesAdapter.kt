package com.diplomaproject.movies.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.diplomaproject.R
import com.diplomaproject.databinding.ItemUpcomingBinding
import com.diplomaproject.movies.model.UpcomingMovie

class UpcomingMoviesAdapter(val context: Context, private val movieList: List<UpcomingMovie>) :
    RecyclerView.Adapter<UpcomingMoviesAdapter.MyViewHolder>() {

    lateinit var binding: ItemUpcomingBinding

    class MyViewHolder(var binding: ItemUpcomingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: UpcomingMovie) {
            binding.movie = movie
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]

        holder.itemView.setOnClickListener(){
            onItemClicked(movie,it)
        }
        holder.bind(movie)
    }

    private fun onItemClicked(movie: UpcomingMovie,view: View) {
        val bundle = Bundle()
        bundle.putParcelable("upcoming",movie)
        view.findNavController().navigate(R.id.action_homeFragment_to_upcomingMovieDetailsFragment,bundle)

    }


}