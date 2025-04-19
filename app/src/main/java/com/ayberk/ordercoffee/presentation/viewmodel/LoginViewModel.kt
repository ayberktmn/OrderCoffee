package com.ayberk.ordercoffee.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {

    private val _userLoggedIn = MutableLiveData<Boolean>()
    val userLoggedIn: LiveData<Boolean> get() = _userLoggedIn

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    init {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    // Google ile giriş için gerekli yapılandırma
    fun setupGoogleSignIn(context: Context, webClientId: String) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // Google ile giriş için giriş intent'ini döndürür
    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    // Google giriş sonucunu işleme
    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            _userLoggedIn.value = false
            _loginError.value = "Google ile giriş başarısız: ${e.message}"
        }
    }

    // Google ile oturum açma
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _userLoggedIn.value = task.isSuccessful
                if (!task.isSuccessful) {
                    _loginError.value = task.exception?.message ?: "Google giriş hatası"
                }
            }
    }

    fun checkIfUserIsAlreadyLoggedIn() {
        val currentUser = firebaseAuth.currentUser
        _userLoggedIn.value = currentUser != null
    }


    // Oturumdan çıkma
    fun signOut() {
        firebaseAuth.signOut()
        _userLoggedIn.value = false
    }
}
