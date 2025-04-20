package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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

        // RecyclerView'i kur
        setupRecyclerView()

        // Sepet ürünlerini gözlemliyoruz
        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketProducts ->
            // Sepet ürünleri değiştikçe RecyclerView'ı güncelliyoruz
            basketAdapter.updateList(basketProducts)
        })

        // Sepet ürünlerini yükle
        basketViewModel.getBasketItems()
    }

    private fun setupRecyclerView() {
        basketAdapter = BasketAdapter(
            basketList = emptyList(),
            onItemClick = { basketProduct ->
                // Burada bir ürün tıklama işlemi ekleyebilirsiniz
                Log.d("BasketFragment", "Product clicked: ${basketProduct.name}")
            },
            onItemDelete = { basketProduct ->
                // Silme işlemi için onItemDelete fonksiyonu
                showDeleteConfirmationDialog(basketProduct)

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

}

