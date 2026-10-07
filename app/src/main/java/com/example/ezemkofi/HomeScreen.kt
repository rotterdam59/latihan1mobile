package com.example.ezemkofi

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ezemkofi.connection.Connection
import kotlinx.coroutines.launch
import org.json.JSONObject

class HomeScreen : AppCompatActivity() {

    private val Con = Connection()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

       val usersseion = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val fullname = usersseion.getString("fullName", "guest")

        val tvFullname = findViewById<TextView>(R.id.tvFullname)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


