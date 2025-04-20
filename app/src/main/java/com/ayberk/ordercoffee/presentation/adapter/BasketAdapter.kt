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
                tvProductPrice.text = "${item.getTotalPrice()}₺" // Toplam fiyatı göster
                tvProductQuantity.text = "Miktar: ${item.quantity}" // Miktar gösterimi
                ivProductImage.setImageResource(item.imageUrl)

                // Azaltma butonuna tıklandığında miktarı azalt
                decreaseQuantity.setOnClickListener {
                    item.decreaseQuantity() // Miktarı azalt
                    tvProductQuantity.text = "Miktar: ${item.quantity}" // Miktarı güncelle
                    tvProductPrice.text = "${item.getTotalPrice()}₺" // Fiyatı güncelle
                }

                // Artırma butonuna tıklandığında miktarı artır
                increaseQuantity.setOnClickListener {
                    item.increaseQuantity() // Miktarı artır
                    tvProductQuantity.text = "Miktar: ${item.quantity}" // Miktarı güncelle
                    tvProductPrice.text = "${item.getTotalPrice()}₺" // Fiyatı güncelle
                }

                // Ürüne tıklandığında yapılacak işlemi belirleyin
                root.setOnClickListener {
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
