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
        R.drawable.kahve,
        R.drawable.kahveler,
        R.drawable.kahveee
    )

    private val categoryList = listOf(
        Category(1, "Sıcak İçecekler"),
        Category(2, "Soğuk İçecekler"),
        Category(3, "Yiyecekler")
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

        // Toolbar başlığını değiştirme
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Anasayfa"  // ActionBar başlığını da değiştirebiliriz

        // ViewPager2 banner
        val adapter = ImagePagerAdapter(this, bannerImages)
        binding.viewPager.adapter = adapter

        // Category RecyclerView
        binding.categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = CategoryAdapter(categoryList)

        // Ürün listesi ve gözlem
        setupRecyclerView()
        observeProducts()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductsAdapter(emptyList()) { selectedProduct ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(selectedProduct)
            findNavController().navigate(action)
        }
        binding.productsRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.productsRecyclerView.layoutManager = layoutManager

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
