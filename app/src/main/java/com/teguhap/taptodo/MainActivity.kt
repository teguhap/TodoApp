package com.teguhap.taptodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = getColor(R.color.utama)

        val ivLogo = findViewById<ImageView>(R.id.ivLogo)

        val animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        ivLogo.animation = animation.apply {
            start()
        }

        Handler().postDelayed({Intent(this,LoginActivity::class.java).also {
            startActivity(it)
        }},2000L)

    }
}