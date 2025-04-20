package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentBasketBinding
import com.ayberk.ordercoffee.presentation.adapter.BasketAdapter
import com.ayberk.ordercoffee.presentation.model.BasketProduct
import com.ayberk.ordercoffee.presentation.viewmodel.BasketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment() {

    private lateinit var binding: FragmentBasketBinding
    private val basketViewModel: BasketViewModel by viewModels()

    private lateinit var basketAdapter: BasketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.include.customToolbar
        toolbar.title = "Sepetim"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // RecyclerView'i kur
        setupRecyclerView()

        // Sepet ürünlerini gözlemliyoruz
        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketProducts ->
            basketAdapter.updateList(basketProducts)

            // Toplam tutarı hesapla ve güncelle
            val totalAmount = basketProducts.sumOf { it.getTotalPrice() }
            binding.tvTotalAmount.text = "₺${totalAmount.toInt()}"
        })

        binding.btnCheckout.setOnClickListener {
            showCheckoutSummary()
        }

        // Sepet ürünlerini yükle
        basketViewModel.getBasketItems()
    }

    fun Double.format(digits: Int) = "%.0f".format(this)

    private fun setupRecyclerView() {
        basketAdapter = BasketAdapter(
            basketList = emptyList(),
            onItemClick = { basketProduct ->
                Log.d("BasketFragment", "Product clicked: ${basketProduct.name}")
            },
            onItemDelete = { basketProduct ->
                showDeleteConfirmationDialog(basketProduct)
            },
            onIncreaseClicked = { basketProduct ->
                basketViewModel.increaseQuantity(basketProduct)
            },
            onDecreaseClicked = { basketProduct ->
                basketViewModel.decreaseQuantity(basketProduct)
            }
        )

        binding.rvBasket.layoutManager = LinearLayoutManager(context)
        binding.rvBasket.adapter = basketAdapter
    }

    private fun showDeleteConfirmationDialog(basketProduct: BasketProduct) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Sepetten Kaldır")
            .setMessage("Bu ürünü sepetinizden kaldırmak istiyor musunuz?")
            .setPositiveButton("Evet") { dialog, _ ->
                basketViewModel.deleteProductById(basketProduct.id)
            }
            .setNegativeButton("Hayır") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()

        val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.coffee_caramel))

        val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negativeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun showCheckoutSummary() {
        val basketItems = basketViewModel.basketItems.value ?: emptyList()

        if (basketItems.isEmpty()) {
            Toast.makeText(requireContext(), "Sepetiniz boş!", Toast.LENGTH_SHORT).show()
            return
        }

        val summary = StringBuilder()
        var totalPrice = 0.0

        basketItems.forEach { item ->
            summary.append("${item.name} x${item.quantity} = ${item.getTotalPrice()}₺\n")
            totalPrice += item.getTotalPrice()
        }

        summary.append("\nToplam: %.2f₺".format(totalPrice))

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Sipariş Özeti")
            .setMessage(summary.toString())
            .setPositiveButton("Onayla") { d, _ ->
                d.dismiss()
                Toast.makeText(requireContext(), "Siparişiniz alındı!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("İptal") { d, _ ->
                d.dismiss()
            }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.coffee_caramel))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        dialog.show()
    }


}

