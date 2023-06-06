package com.diplomaproject.movies.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.diplomaproject.MovieDetailsFragment
import com.diplomaproject.MovieList
import com.diplomaproject.R
import com.diplomaproject.databinding.ItemBinding
import com.diplomaproject.movies.model.Movie

class MyAdapter(val context: Context, private val movieList: List<Movie>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    lateinit var binding: ItemBinding

    class MyViewHolder(var binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

    private fun onItemClicked(movie: Movie,view: View) {
        val bundle = Bundle()
        bundle.putParcelable("movie",movie)
        view.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment,bundle)

    }


}