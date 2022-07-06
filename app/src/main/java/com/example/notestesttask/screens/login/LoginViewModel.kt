package com.example.notestesttask.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notestesttask.base.NotesApp
import com.example.notestesttask.util.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var loginData = MutableLiveData<FirebaseUser>()
    private var errorLogin = MutableLiveData<String>()
    private var loading = MutableLiveData<Boolean>()

    fun tryToLogin(email: String, password: String, confirmPassword: String) {
        when {
            confirmPassword.isEmpty() -> {
                signIn(email, password)
            }
            password == confirmPassword -> {
                register(email, password)
            }
            else -> {
                errorLogin.value = Constants.ERROR_INVALID_CONFIRM_PASSWORD
            }
        }
    }

    private fun register(email: String, password: String) {
        loading.value = true
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                loading.value = false
                processResult(result)
            }
    }

    private fun signIn(email: String, password: String) {
        loading.value = true
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                loading.value = false
                processResult(result)
            }
    }

    fun getLoginData(): LiveData<FirebaseUser> = loginData
    fun getErrorLogin(): LiveData<String> = errorLogin
    fun isLoading(): LiveData<Boolean> = loading

    private fun processResult(result: Task<AuthResult>) {
        if (result.isSuccessful) {
            val user = result.result.user
            NotesApp.sharedPreferencesManager.saveUserEmail(user?.email)
            loginData.value = result.result.user
        } else {
            errorLogin.value = result.exception?.message
        }
    }
}