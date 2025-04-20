package com.ayberk.ordercoffee.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.ItemProductBinding
import com.ayberk.ordercoffee.presentation.model.Product
import com.bumptech.glide.Glide

class ProductsAdapter(
    private var productList: List<Product>,
    private val onItemClick: (Product) -> Unit,
    private val onAddToBasketClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "${product.price}₺"

            // Eğer imageUrl bir String (URL) ise Glide kullan:
            Glide.with(ivProductImage.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.kahveler) // varsa
                .into(ivProductImage)

            btnBuy.setOnClickListener {
                onAddToBasketClick(product)
            }

            root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = productList.size
}
