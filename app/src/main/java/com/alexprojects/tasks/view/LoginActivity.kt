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

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)

        viewModel.verifyAutentication()
        supportActionBar?.hide()

        observe()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_login -> handleLogin()
            R.id.text_register -> showRegisterActivity()
        }
    }

    private fun biometricAutentication() {
        if (BiometricHelper.isBiometricAvalible(this)) {

            val executor = ContextCompat.getMainExecutor(this)

            val bio = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    showMainActivity()
                    finish()
                    super.onAuthenticationSucceeded(result)
                }

            })
            val info = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticação Biométrica")
                .setSubtitle("")
                .setDescription("")
                .setNegativeButtonText("Cancelar")
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

    private fun observe() {
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

    private fun handleLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        viewModel.doLogin(email, password)
    }
}