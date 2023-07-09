package com.diplomaproject.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diplomaproject.R
import com.diplomaproject.movies.model.OrderItem

class OrderAdapter(orderItems1: Context, private val orderItems: List<OrderItem>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_layout, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = orderItems[position]
        holder.bind(currentOrder)
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
//        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(orderItem: OrderItem) {
//            titleTextView.text = orderItem.title
//            descriptionTextView.text = orderItem.description
        }
    }
}
