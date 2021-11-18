package com.teguhap.taptodo

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.teguhap.taptodo.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val loader = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.apply {


            btnRegist.setOnClickListener {
                val email = etEmailRegist.text.toString().trim()
                val password = etPasswordRegist.text.toString().trim()
                val cPassword = etConfirmPasword.text.toString().trim()


                if (email.isEmpty()){
                    etEmailRegist.error = "Email is Required"
                    return@setOnClickListener
                }

                if(password.isEmpty()){
                    etPasswordRegist.error = "Password is Required"
                    return@setOnClickListener
                }else{

                    if (password != cPassword){
                        etConfirmPasword.error = "Passwords are not the same"
                    }else{
                        loader.setMessage("Registration in progress")
                        loader.setCanceledOnTouchOutside(false)
                        loader.show()
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                            OnCompleteListener {
                                if(it.isSuccessful){
                                    val intent = Intent(this@RegistrationActivity,HomeActivity :: class.java)
                                        startActivity(intent)
                                        finish()
                                    loader.dismiss()
                                }else{
                                    val error = it.exception.toString()
                                    if(error == "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted."){
                                        Toast.makeText(this@RegistrationActivity,"Format email tidak benar",Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(this@RegistrationActivity,error,Toast.LENGTH_LONG).show()
                                    }

                                    loader.dismiss()
                                }

                            })
                    }//tutup else password tidak kosong

                }//tutup else check password


            }//tutup btnRegist click listener




        }
    }
}