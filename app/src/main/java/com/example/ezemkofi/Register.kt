package com.example.ezemkofi

import android.content.Intent
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        tvLogin.paintFlags = tvLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        tvLogin.setOnClickListener()
        {
            val intentToHomeActivity = Intent(this, MainActivity::class.java)
            startActivity(intentToHomeActivity)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}