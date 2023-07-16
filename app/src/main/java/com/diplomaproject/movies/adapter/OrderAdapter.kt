package com.diplomaproject.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.diplomaproject.R
import com.diplomaproject.databinding.ItemBinding
import com.diplomaproject.databinding.OrderLayoutBinding
import com.diplomaproject.movies.model.OrderItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderAdapter(val context: Context, private val orderItems: ArrayList<OrderItem>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private lateinit var binding: OrderLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        binding = OrderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderItems[position]

        holder.binding.deleteBtn.setOnClickListener(){
            FirebaseFirestore.getInstance().collection("Tickets")
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Users")
                .document(order.id)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Successfully deleted!",
                        Toast.LENGTH_LONG
                    ).show()
                    orderItems.removeAt(position)
                    notifyItemRangeRemoved(position, orderItems.size)
                    notifyItemRangeChanged(position, orderItems.size)
                    notifyDataSetChanged()
                }
        }
        holder.bind(order)
    }


    override fun getItemCount(): Int {
        return orderItems.size
    }
    class OrderViewHolder(var binding: OrderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderItem: OrderItem) {
            binding.order = orderItem
        }
    }

}
