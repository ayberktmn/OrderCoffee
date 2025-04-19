package com.ayberk.ordercoffee.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ayberk.ordercoffee.R
import com.ayberk.ordercoffee.databinding.FragmentLoginBinding
import com.ayberk.ordercoffee.presentation.viewmodel.LoginViewModel
import com.ayberk.ordercoffee.util.PreferenceManager

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    private val sharedPreferences: SharedPreferences
        get() = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Check if the user is already logged in
        if (PreferenceManager.isLoggedIn(requireContext())) {
            // If user is already logged in, navigate directly to HomeFragment
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return
        }

        viewModel.checkIfUserIsAlreadyLoggedIn()

        // Set the onClickListener for the login button
        binding.btnLogin.setOnClickListener {
            LoginEmailPassword()
        }

        // Google Sign-In setup
        viewModel.setupGoogleSignIn(requireContext(), getString(R.string.web_client_id))

        // Google sign-in button click
        binding.googleSigninButton.setOnClickListener {
            googleSignIn()
        }

        // Handle sign-in result
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.handleSignInResult(result.data)
                } else {
                    Toast.makeText(requireContext(), "Google giriş iptal edildi", Toast.LENGTH_SHORT).show()
                }
            }

        // Observe login results
        observeLoginResults()
    }

    private fun googleSignIn() {
        val signInIntent = viewModel.getSignInIntent()
        googleSignInLauncher.launch(signInIntent)
    }

    private fun observeLoginResults() {
        // Observe login status and navigate accordingly
        viewModel.userLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                // After successful login, save the login state in SharedPreferences
                PreferenceManager.setLoggedIn(requireContext(), true)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                Toast.makeText(requireContext(), "Giriş başarılı!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Giriş başarısız oldu!", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe login error messages
        viewModel.loginError.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), "Giriş başarısız: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to handle email/password login
    // Function to handle email/password login
    private fun LoginEmailPassword() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        // Check if email is empty
        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Lütfen e-posta adresinizi girin.", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Geçerli bir e-posta adresi girin.", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if password is empty
        if (password.isEmpty()) {
            Toast.makeText(requireContext(), "Lütfen şifrenizi girin.", Toast.LENGTH_SHORT).show()
            return
        }

        // If validation passes, save the credentials
        saveCredentials(email, password)

        Toast.makeText(requireContext(), "E-posta: $email", Toast.LENGTH_SHORT).show()

        // After saving, navigate to the HomeFragment (successful login)
        Toast.makeText(requireContext(), "Giriş başarılı!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    // Function to save email and password in SharedPreferences
    private fun saveCredentials(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
