package com.example.ezemkofi

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)

        val orangmasuk = intent.getStringExtra("fullname")
        val tvFullname = findViewById<TextView>(R.id.tvFullname)
        tvFullname.text = "$orangmasuk"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


//package com.example.ezemkofi
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import com.example.ezemkofi.connection.Connection
//import kotlinx.coroutines.launch
//import org.json.JSONObject
//
//class HomeScreen : AppCompatActivity() {
//
//    private val connection = Connection()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home_screen)   // sesuaikan dengan nama layout HomeScreen-mu
//        val welcomeTextView = findViewById<TextView>(R.id.tvFullname)   // TextView di layout HomeScreen
//        val userSessionPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
//        val savedToken = userSessionPreferences.getString("TOKEN", "") ?: ""
//
//        lifecycleScope.launch {
//            val userInformationJson = connection.getCurrentUser(
//                "http://10.0.2.2:5001/api/me",
//                savedToken
//            )
//            Log.d("ME", "Isi /api/me: $userInformationJson")   // cek nama field asli di Logcat
//
//            if (userInformationJson != null) {
//                try {
//                    val fullName = JSONObject(userInformationJson).optString("fullname")
//                    welcomeTextView.text = "Welcome, $fullName"
//                } catch (exception: Exception) {
//                    Log.e("ME", "Isi /api/me bukan JSON yang diharapkan", exception)
//                }
//            }
//        }
//    }
//}