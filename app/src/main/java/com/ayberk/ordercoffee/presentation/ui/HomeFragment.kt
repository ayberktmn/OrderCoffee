package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentHomeBinding
import com.ayberk.ordercoffee.presentation.model.Category
import com.ayberk.ordercoffee.presentation.adapter.CategoryAdapter
import com.ayberk.ordercoffee.presentation.adapter.ImagePagerAdapter
import com.ayberk.ordercoffee.presentation.adapter.ProductsAdapter
import com.ayberk.ordercoffee.presentation.viewmodel.LoginViewModel
import com.ayberk.ordercoffee.presentation.viewmodel.ProductViewModel
import com.ayberk.ordercoffee.util.PreferenceManager

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var productAdapter: ProductsAdapter

    private val productViewModel: ProductViewModel by viewModels()

    private val bannerImages = listOf(
        "https://www.deryauluduz.com/wp-content/uploads/2021/10/kahve-ne-ise-yariyor.jpg",
        "https://staresso.com/cdn/shop/articles/What_is_a_latte.jpg?v=1716606481",
        "https://www.unileverfoodsolutions.com.tr/konsept-uygulamalarimiz/yemek-trendleri/soguk-kahve-rehberi/jcr:content/parsys/content/image_copy_copy_1171292015.img.jpg/1627594954156.jpg"
    )

    private val categoryList = listOf(
        Category(1, "Tümü"),
        Category(2, "Sıcak İçecekler"),
        Category(3, "Soğuk İçecekler"),
        Category(4, "Yiyecekler")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val toolbar = binding.include.customToolbar
        toolbar.title = "Anasayfa"

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // ViewPager2 banner
        val adapter = ImagePagerAdapter(this, bannerImages)
        binding.viewPager.adapter = adapter

        // Category RecyclerView
        binding.categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = CategoryAdapter(categoryList) { selectedCategory ->
            productViewModel.filterProductsByCategory(selectedCategory)
        }

        setupRecyclerView()
        observeProducts()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductsAdapter(emptyList()) { selectedProduct ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(selectedProduct)
            findNavController().navigate(action)
        }

        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productAdapter
        }
    }


    private fun observeProducts() {
        productViewModel.productList.observe(viewLifecycleOwner) { productList ->
            productAdapter = ProductsAdapter(productList) { selectedProduct ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(selectedProduct)
                findNavController().navigate(action)
            }
            binding.productsRecyclerView.adapter = productAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
