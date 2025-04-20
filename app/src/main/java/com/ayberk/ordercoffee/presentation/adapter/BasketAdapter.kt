package com.ayberk.ordercoffee.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.ordercoffee.databinding.ItemBasketBinding
import com.ayberk.ordercoffee.presentation.model.BasketProduct

class BasketAdapter(
    private var basketList: List<BasketProduct>,
    private val onItemClick: (BasketProduct) -> Unit
) : RecyclerView.Adapter<BasketAdapter.BasketItemViewHolder>() {

    inner class BasketItemViewHolder(private val binding: ItemBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BasketProduct) {
            binding.apply {
                // Ürün adı ve fiyatını yerleştir
                tvProductName.text = item.name
                tvProductPrice.text = "${item.price}₺"
                tvProductQuantity.text = "Miktar: ${item.quantity}"

                // Ürün resmini yerleştir
                // Eğer imageUrl URL ise Glide veya Picasso gibi kütüphaneleri kullanabilirsiniz
                // Glide.with(itemView.context).load(item.imageUrl).into(ivProductImage)

                root.setOnClickListener {
                    // Ürüne tıklandığında yapılacak işlemi belirleyin
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketItemViewHolder, position: Int) {
        holder.bind(basketList[position])
    }

    override fun getItemCount(): Int = basketList.size

    // Listeyi güncellemek için kullanılır
    fun updateList(newList: List<BasketProduct>) {
        basketList = newList
        notifyDataSetChanged()
    }
}
