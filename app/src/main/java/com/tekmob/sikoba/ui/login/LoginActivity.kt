package com.tekmob.sikoba.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.tekmob.sikoba.auth.UserPreference
import com.tekmob.sikoba.dataStore
import com.tekmob.sikoba.databinding.ActivityLoginBinding
import com.tekmob.sikoba.ui.ViewModelFactory
import com.tekmob.sikoba.ui.petugas.PetugasMainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun setupAction(){
        binding.buttonMasuk.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            when {
                username != "petugas" -> {
                    binding.layoutUsername.error = "Username salah"
                }
                password != "petugas" -> {
                    binding.layoutPassword.error = "Password salah"
                }
                else -> {
                    viewModel.login()
                    AlertDialog.Builder(this).apply {
                        setTitle("Berhasil!")
                        setMessage("Anda berhasil login sebagai admin!")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, PetugasMainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }
}