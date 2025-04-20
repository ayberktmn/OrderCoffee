package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentDetailsBinding
import com.ayberk.ordercoffee.presentation.model.Product
import com.ayberk.ordercoffee.presentation.viewmodel.FavoriteViewModel
import com.ayberk.ordercoffee.presentation.model.FavoriteProduct
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

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

        val product = args.selectedProduct // Selected product information

        // Set up the toolbar
        val toolbar = binding.include.customToolbar
        toolbar.title = "Product Details"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Enable the back button in the toolbar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle back button click event
        toolbar.setNavigationOnClickListener {
            // Navigate back when the back button is clicked
            requireActivity().onBackPressed()
        }

        binding.apply {
            // Set product details
            coffeeName.text = product.name
            coffeePrice.text = "${product.price}₺"

            Glide.with(requireContext())
                .load(product.imageUrl)
                .into(coffeeImage)

            // Check if the product is a favorite and update UI
            favoriteViewModel.checkIfFavorite(product.id)

            // Observe the favorite status and update the favorite icon
            favoriteViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
                val iconRes = if (isFavorite) R.drawable.favoritee else R.drawable.notfavorite
                favoriteIcon.setImageResource(iconRes)
            }

            // Add or remove product from favorites when clicked
            favoriteIcon.setOnClickListener {
                val favoriteProduct = product.toFavoriteProduct()

                if (favoriteViewModel.isFavorite.value == true) {
                    // Remove product from favorites
                    favoriteViewModel.deleteFavoriteProduct(favoriteProduct)
                } else {
                    // Add product to favorites
                    favoriteViewModel.addFavoriteProduct(favoriteProduct)
                }
            }
        }
    }

    // Convert Product to FavoriteProduct
    private fun Product.toFavoriteProduct(): FavoriteProduct {
        return FavoriteProduct(
            id = this.id,
            name = this.name,
            price = this.price,
            imageUrl = this.imageUrl,
            category = this.categoryName
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
