package com.ayberk.ordercoffee.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.presentation.model.Category
import com.google.android.material.button.MaterialButton

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var selectedPosition = -1

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnCategory: MaterialButton = itemView.findViewById(R.id.btnCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.btnCategory.text = category.name

        if (position == selectedPosition) {
            holder.btnCategory.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.coffee_espresso)
            )
            holder.btnCategory.setTextColor(Color.WHITE)
        } else {
            holder.btnCategory.setBackgroundColor(Color.WHITE)
            holder.btnCategory.setTextColor(Color.BLACK)
        }

        holder.btnCategory.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()

            onCategoryClick(category.name) // TIKLANAN KATEGORİYİ GÖNDERİYORUM
        }
    }
}

