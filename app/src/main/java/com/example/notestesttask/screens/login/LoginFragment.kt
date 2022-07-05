package com.example.notestesttask.screens.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.notestesttask.R
import com.example.notestesttask.base.BaseFragment
import com.example.notestesttask.base.NotesApp
import com.example.notestesttask.data.SharedPreferencesManager
import com.example.notestesttask.databinding.LoginFragmentBinding
import com.example.notestesttask.util.KeyboardUtil
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun getContentView(): Int = R.layout.login_fragment

    override fun setupDataBinding(dataBinding: ViewDataBinding?) {
        binding = dataBinding as LoginFragmentBinding
        binding.fragment = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        initUi()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getLoginData().observe(viewLifecycleOwner) { user ->
            if (user != null) goToNotesListFragment()
        }

        viewModel.getErrorLogin().observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, getErrorMessage(error), Toast.LENGTH_LONG).show()
        }

        viewModel.isLoading().observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun goToNotesListFragment() {
        NavHostFragment
            .findNavController(this)
            .navigate(LoginFragmentDirections.actionLoginFragmentToNotesFragment())
    }

    private fun initUi() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            NotesApp.sharedPreferencesManager.saveUserEmail(currentUser.email)
            goToNotesListFragment()
        } else {
            showRegistrationField()
        }
    }

    fun showRegistrationField() {
        clearFields()
        binding.etConfirmPassword.visibility = View.VISIBLE
        binding.tvDontHaveAccount.visibility = View.GONE
        binding.tvHaveAccount.visibility = View.VISIBLE
        binding.tvLogin.text = getString(R.string.registration)
        binding.btnLogin.text = getString(R.string.register)
    }

    fun showLoginField() {
        clearFields()
        binding.etConfirmPassword.visibility = View.GONE
        binding.tvDontHaveAccount.visibility = View.VISIBLE
        binding.tvHaveAccount.visibility = View.GONE
        binding.tvLogin.text = getString(R.string.login)
        binding.btnLogin.text = getString(R.string.enter)
    }

    fun onLoginClick() {
        KeyboardUtil.hideKeyboard(requireActivity())

        viewModel.tryToLogin(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString(),
            binding.etConfirmPassword.text.toString()
        )
    }

    private fun clearFields() {
        binding.etEmail.setText("")
        binding.etPassword.setText("")
        binding.etConfirmPassword.setText("")
        binding.etEmail.requestFocus()
    }

}