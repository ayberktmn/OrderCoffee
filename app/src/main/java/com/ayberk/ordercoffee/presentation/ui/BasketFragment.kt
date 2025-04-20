package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBasketBinding.bind(view)

        setupRecyclerView()

        // Sepet ürünlerini gözlemliyoruz
        basketViewModel.basketItems.observe(viewLifecycleOwner, Observer { basketProducts ->
            basketAdapter.updateList(basketProducts) // Sepet ürünleri değiştikçe RecyclerView'ı güncelliyoruz
        })
    }

    private fun setupRecyclerView() {
        basketAdapter = BasketAdapter(emptyList()) { basketProduct ->
            // Sepetteki ürünle ilgili işlem yapılabilir (örneğin silme)
        //    basketViewModel.addProductToBasket(basketProduct)
        }

        binding.rvBasket.layoutManager = LinearLayoutManager(context)
        binding.rvBasket.adapter = basketAdapter
    }
}
