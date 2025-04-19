package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentDetailsBinding
import com.ayberk.ordercoffee.presentation.model.Product
import com.ayberk.ordercoffee.presentation.viewmodel.FavoriteViewModel
import com.ayberk.ordercoffee.room.FavoriteProduct
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.selectedProduct // Seçilen ürün bilgisi

        // Toolbar'ı ayarlama
        val toolbar = binding.include.customToolbar
        toolbar.title = "Ürün Detayı"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        binding.apply {
            // Kahve bilgilerini bağlama
            coffeeName.text = product.name
            coffeePrice.text = "${product.price}₺"

            Glide.with(requireContext())
                .load(product.imageUrl)
                .into(coffeeImage)

            // Favoriye ekleme butonu tıklama işlemi
            favoriteIcon.setOnClickListener {
                // Seçilen kahve ürününü favorilere ekleme
                val favoriteProduct = product.toFavoriteProduct()

                // ViewModel'e ekleme işlemi için çağrı yapıyoruz
                favoriteViewModel.addFavoriteProduct(favoriteProduct)
            }
        }
    }

    // Product'ı FavoriteProduct'a dönüştürme
    private fun Product.toFavoriteProduct(): FavoriteProduct {
        return FavoriteProduct(
            id = this.id, // Ürün ID'sini ekliyoruz
            name = this.name,
            price = this.price,
            imageUrl = this.imageUrl.toString(),
            category = this.categoryName
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
