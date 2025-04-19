package com.ayberk.ordercoffee.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentProfileBinding
import com.ayberk.ordercoffee.presentation.viewmodel.LoginViewModel
import com.ayberk.ordercoffee.util.PreferenceManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val toolbar = binding.include.customToolbar
        toolbar.title = "Profil"

        (activity as AppCompatActivity).setSupportActionBar(toolbar)


        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
            logout()

            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }, 100)
        }
    }

    private fun logout() {
        PreferenceManager.clear(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
