package com.example.ezemkofi

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.graphics.Paint
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ezemkofi.connection.Connection
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private val Con = Connection()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)




        btnLogin.setOnClickListener()
        {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            lifecycleScope.launch {
                val response = Con.loginUser(
                    "http://10.0.2.2:5000/api/auth",
                    username,
                    password
                )
                if (response != null) {

                    val fullname = Con.getMe("http://10.0.2.2:5000/api/me", response)

                    val usersession = getSharedPreferences("UserSession", MODE_PRIVATE)
                    usersession.edit()
                        .putString("USERNAME", username)
                        .putString("fullName",fullname)
                        .apply()
                    val Intent = Intent(this@MainActivity, HomeScreen::class.java)
                    startActivity(Intent)

                } else {
                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvRegister.paintFlags = tvRegister.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        tvRegister.setOnClickListener()
        {
            val intentToHomeActivity = Intent(this, Register::class.java)
            startActivity(intentToHomeActivity)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}