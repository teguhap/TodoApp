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
        val currentUser = mAuth.currentUser


        if(currentUser!=null){
            Intent(this,HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                finish()
            }
        }

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
                                if(error == "com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted."
                                    ||error == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted."){
                                Toast.makeText(this@LoginActivity,"Username salah atau belum terdaftar",Toast.LENGTH_LONG).show()
                                loader.dismiss()}
                                else{
                                    Toast.makeText(this@LoginActivity,"Password salah",Toast.LENGTH_SHORT).show()
                                    loader.dismiss()
                                    }
                            }
                        })
                }

            }


        }


    }
}