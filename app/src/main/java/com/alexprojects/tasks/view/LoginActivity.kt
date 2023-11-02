package com.alexprojects.tasks.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.alexprojects.tasks.helper.BiometricHelper
import com.alexprojects.tasks.R
import com.alexprojects.tasks.databinding.ActivityLoginBinding
import com.alexprojects.tasks.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        subscriptionUI()
    }
    private fun setupUI() {
        supportActionBar?.hide()
    }

    private fun subscriptionUI() {
        viewModel.verifyAutentication()
        binding.buttonLogin.setOnClickListener { handleLogin() }
        binding.textRegister.setOnClickListener {showRegisterActivity()}

        viewModel.login.observe(this) {
            if (it.status()) {
                showMainActivity()
                finish()
            } else {
                Toast.makeText(applicationContext, it.message(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loggedUser.observe(this) {
            if (it) {
                biometricAutentication()
            }
        }

    }

    private fun biometricAutentication() {
        if (BiometricHelper.isBiometricAvalible(this)) {

            val executor = ContextCompat.getMainExecutor(this)

            val bio =
                BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        showMainActivity()
                        finish()
                        super.onAuthenticationSucceeded(result)
                    }

                })
            val info = BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_title))
                .setNegativeButtonText(getString(R.string.biometric_cancelar))
                .build()
            bio.authenticate(info)
        }

    }

    private fun showMainActivity() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun showRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun handleLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        viewModel.doLogin(email, password)
    }
}