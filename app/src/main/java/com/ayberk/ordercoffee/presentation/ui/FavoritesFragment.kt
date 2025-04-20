package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.ordercoffee.databinding.FragmentFavoritesBinding
import com.ayberk.ordercoffee.presentation.adapter.FavoriteAdapter
import com.ayberk.ordercoffee.presentation.viewmodel.FavoriteViewModel
import com.ayberk.ordercoffee.room.FavoriteProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    // Loading spinner'ı
    private lateinit var loadingSpinner: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.include.customToolbar
        toolbar.title = "Favorilerim"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())

        favoriteAdapter = FavoriteAdapter(emptyList())
        binding.recyclerFavorites.adapter = favoriteAdapter

        // Loading spinner'ı buluyoruz
        loadingSpinner = binding.loadingSpinner

        // Favori ürünleri gözlemliyoruz
        observeFavoriteProducts()
        favoriteViewModel.loadFavoriteProducts()
    }

    private fun observeFavoriteProducts() {
        favoriteViewModel.favoriteProducts.observe(viewLifecycleOwner) { productList ->
            favoriteAdapter.updateList(productList)
        }

        // isLoading durumu gözlemi
        favoriteViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Loading durumuna göre spinner'ı göster/gizle
            if (isLoading) {
                loadingSpinner.visibility = View.VISIBLE
            } else {
                loadingSpinner.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
