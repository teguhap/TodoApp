package com.teguhap.taptodo

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.teguhap.taptodo.databinding.ActivityLoginBinding
import java.security.PermissionCollection

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loader = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        window.statusBarColor = getColor(R.color.start_utama_gradient)


        binding.apply {
            tvRegister.setOnClickListener{
                Intent(this@LoginActivity,RegistrationActivity :: class.java).also {
                    startActivity(it)
                }
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                Log.d("TestLogin","$email,$password")

                if(email.isEmpty()){
                    etEmail.error = "Email is required"
                }
                if(password.isEmpty()){
                    etPassword.error = "Password is required"
                }else{
                    loader.setMessage("Login in Progress")
                    loader.setCanceledOnTouchOutside(false)
                    loader.show()

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                        OnCompleteListener {
                            if(it.isSuccessful){
                               val intent =  Intent(this@LoginActivity,HomeActivity :: class.java)
                                startActivity(intent)
                                finish()
                                loader.dismiss()
                            }else{
                                val error = it.exception.toString()
                                Toast.makeText(this@LoginActivity,"Login Failed $error",Toast.LENGTH_SHORT).show()
                                loader.dismiss()
                            }
                        })
                }

            }


        }


    }
}