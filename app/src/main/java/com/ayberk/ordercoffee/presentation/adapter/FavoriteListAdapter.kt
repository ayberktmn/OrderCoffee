package com.ayberk.ordercoffee.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.ordercoffee.databinding.ItemFavoriteBinding
import com.ayberk.ordercoffee.presentation.model.FavoriteProduct

class FavoriteAdapter(
    private var favoriteList: List<FavoriteProduct>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteItemViewHolder>() {

    inner class FavoriteItemViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteProduct) {
            binding.apply {
                coffeeName.text = item.name
                coffeePrice.text = "${item.price}₺"

                imageFavorite.setImageResource(item.imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size

    fun updateList(newList: List<FavoriteProduct>) {
        favoriteList = newList
        notifyDataSetChanged()
    }
}
