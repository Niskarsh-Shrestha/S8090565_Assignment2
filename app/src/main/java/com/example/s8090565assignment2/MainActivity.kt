package com.example.s8090565assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        etFirstName = findViewById(R.id.etFirstName)
        etStudentId = findViewById(R.id.etStudentId)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()

            // Skip credential validation and proceed directly to dashboard
            if (firstName.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
            } else {
                // No login validation here, directly go to DashboardActivity
                performLogin()
            }
        }
    }

    // Directly proceed to DashboardActivity
    private fun performLogin() {
        // Instead of authentication, just pass any data and go to DashboardActivity
        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}
