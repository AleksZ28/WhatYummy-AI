package com.ali.whatyummyai.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ali.whatyummyai.R

class KitchenAdapter (
    private var items: MutableList<String>,
    private val onRemoveClick: (String) -> Unit
    ) : RecyclerView.Adapter<KitchenAdapter.KitchenViewHolder>() {
        class KitchenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvIngredient: TextView = view.findViewById(R.id.tvIngredient)
            val btnRemove: ImageView = view.findViewById(R.id.ivRemove)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitchenViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_kitchen, parent, false)
            return KitchenViewHolder(view)
        }

        override fun onBindViewHolder(holder: KitchenViewHolder, position: Int) {
            val ingredient = items[position]
            holder.tvIngredient.text = ingredient
            holder.btnRemove.setOnClickListener {
                val index = items.indexOf(ingredient)
                if (index != -1) {
                    items.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
        }

        override fun getItemCount() = items.size

        fun addItem(ingredient: String) {
            items.add(ingredient)
            notifyItemInserted(items.size - 1)
        }

        fun currentItems(): List<String> = items.toList()
    }
