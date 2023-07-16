package com.diplomaproject.movies.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.diplomaproject.R
import com.diplomaproject.authentication.Login
import com.diplomaproject.authentication.data.User
import com.diplomaproject.databinding.FragmentOrderBinding
import com.diplomaproject.movies.adapter.OrderAdapter
import com.diplomaproject.movies.model.Movie
import com.diplomaproject.movies.model.OrderItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class OrderFragment : Fragment() {

    lateinit var binding: FragmentOrderBinding
    lateinit var adapter: OrderAdapter
    lateinit var auth: FirebaseAuth
    lateinit var orderList: MutableList<OrderItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        orderItems()
        return binding.root
    }

    private fun orderItems() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        orderList = arrayListOf<OrderItem>()
        FirebaseFirestore.getInstance().collection("User_Tickets")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("Users").get().addOnSuccessListener { documentSnapshot ->
                for (i in documentSnapshot) {
                    orderList.add(
                        OrderItem(
                            i["movie_name"].toString(),
                            i["banner_image_url"].toString(),
                            i["room"].toString(),
                            i["date"].toString(),
                            i["seating_no"].toString(),
                            i["price"].toString(),
                            i["quantity"].toString(),
                            i["total_price"].toString(),
                            i["time"].toString(),
                            i["id"].toString()
                        )
                    )
                }
                adapter = OrderAdapter(binding.root.context, orderList as ArrayList<OrderItem>)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
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
