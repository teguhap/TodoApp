package com.teguhap.taptodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.teguhap.taptodo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        window.statusBarColor = getColor(R.color.utama)

        binding.apply {
            tvRegister.setOnClickListener{
                Intent(this@LoginActivity,RegistrationActivity :: class.java).also {
                    startActivity(it)
                }
            }


        }


    }
}