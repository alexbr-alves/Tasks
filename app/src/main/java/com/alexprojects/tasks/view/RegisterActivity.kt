package com.alexprojects.tasks.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alexprojects.tasks.R
import com.alexprojects.tasks.databinding.ActivityRegisterBinding
import com.alexprojects.tasks.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setupUI()
        subscriptionUI()
        setContentView(binding.root)
    }

    private fun setupUI() {
        supportActionBar?.hide()
    }

    private fun subscriptionUI() {
        binding.buttonSave.setOnClickListener { handleSave() }
        viewModel.user.observe(this) {
            if (it.status()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSave() {
        val nome = binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        viewModel.create(nome, email, password)
    }
}