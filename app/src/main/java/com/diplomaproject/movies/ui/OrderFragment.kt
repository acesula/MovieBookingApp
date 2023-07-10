package com.diplomaproject.movies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.diplomaproject.R
import com.diplomaproject.databinding.FragmentOrderBinding
import com.diplomaproject.movies.adapter.OrderAdapter

class OrderFragment : Fragment() {

    lateinit var binding: FragmentOrderBinding
    lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)

//        binding.orderRecyclerView.setHasFixedSize(true)
//
//        val orderItems = listOf(
//            OrderItem("Item 1"),
//            OrderItem("Item 2"),
//            OrderItem("Item 3")
//            // Add more items as needed
//        )
//
//        adapter = OrderAdapter(binding.root.context, orderItems)
//        binding.orderRecyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()

        return binding.root
    }

}