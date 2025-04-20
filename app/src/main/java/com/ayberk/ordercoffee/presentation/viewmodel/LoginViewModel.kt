package com.ayberk.ordercoffee.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
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
    fun handleSignInResult(account: GoogleSignInAccount) {
        // Google'dan gelen ID Token'i alıyoruz
        val idToken = account.idToken

        if (idToken != null) {
            // Firebase Authentication ile Google girişini yapıyoruz
            firebaseAuthWithGoogle(idToken)
        } else {
            // Token alınamazsa hata mesajı
            _loginError.value = "Google ID token alınamadı."
            Log.e("LoginViewModel", "Google ID token alınamadı.")
        }
    }

    // Google ile oturum açma
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Giriş başarılı, kullanıcının oturumu açıldı
                    val user = firebaseAuth.currentUser
                    // Kullanıcı verilerini işle
                    Log.d("LoginViewModel", "Google ile giriş başarılı: ${user?.displayName}")
                    _userLoggedIn.value = true
                } else {
                    // Giriş başarısız oldu
                    _loginError.value = "Google ile giriş başarısız"
                    Log.e("LoginViewModel", "Google ile giriş başarısız", task.exception)
                    _userLoggedIn.value = false
                }
            }
    }

    // Kullanıcının oturum açıp açmadığını kontrol etme
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
