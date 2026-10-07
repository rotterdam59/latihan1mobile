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


            if(username.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            lifecycleScope.launch {
                val response = Con.loginUser(
                    "http://10.0.2.2:5000/api/auth",
                    username,
                    password
                    )
                if (response != null)
                {
                    val Intent = Intent(this@MainActivity, HomeScreen::class.java)
                    startActivity(Intent)

                }
                else
                {
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
}//
////package com.example.ezemkofi
////
////import android.content.Intent
////import android.graphics.Paint
////import android.os.Bundle
////import android.widget.Button
////import android.widget.EditText
////import android.widget.TextView
////import android.widget.Toast
////import androidx.activity.enableEdgeToEdge
////import androidx.appcompat.app.AppCompatActivity
////import androidx.core.view.ViewCompat
////import androidx.core.view.WindowInsetsCompat
////import androidx.lifecycle.lifecycleScope
////import com.example.ezemkofi.connection.Connection
////import kotlinx.coroutines.launch
////
////class MainActivity : AppCompatActivity() {
////
////    private val connection = Connection()
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
////        setContentView(R.layout.activity_main)
////
////        val usernameEditText = findViewById<EditText>(R.id.etUsername)
////        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
////        val loginButton = findViewById<Button>(R.id.buttonLogin)
////        val registerTextView = findViewById<TextView>(R.id.tvRegister)
////
////        loginButton.setOnClickListener {
////            val username = usernameEditText.text.toString()
////            val password = passwordEditText.text.toString()
////
////            if (username.isEmpty() || password.isEmpty()) {
////                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
////                return@setOnClickListener
////            }
////
////            lifecycleScope.launch {
////                // Port 5000 = HTTP. Port 5001 = HTTPS, jangan dipakai dari emulator.
////                val token = connection.loginUser(
////                    "http://10.0.2.2:5000/api/auth",
////                    username,
////                    password
////                )
////
////                if (token != null) {
////                    // Respons login = token teks biasa. Simpan, dipakai screen lain untuk panggil API.
////                    val userSessionPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
////                    userSessionPreferences.edit()
////                        .putString("TOKEN", token.trim())
////                        .putString("USERNAME", username)
////                        .apply()
////
////                    val homeScreenIntent = Intent(this@MainActivity, HomeScreen::class.java)
////                    startActivity(homeScreenIntent)
////                } else {
////                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
////                }
////            }
////        }
////
////        registerTextView.paintFlags = registerTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
////
////        registerTextView.setOnClickListener {
////            val registerIntent = Intent(this, Register::class.java)
////            startActivity(registerIntent)
////        }
////
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
////            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
////            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
////            insets
////        }
////    }
//}