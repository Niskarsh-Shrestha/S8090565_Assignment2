package com.example.s8090565assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.s8090565assignment2.model.AuthRequest
import com.example.s8090565assignment2.network.ApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var etFirstName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFirstName = findViewById(R.id.etFirstName)
        etStudentId = findViewById(R.id.etStudentId)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()

            if (firstName.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
            } else {
                authenticateUser(firstName, studentId)
            }
        }
    }

    private fun authenticateUser(firstName: String, studentId: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.authenticate(AuthRequest(firstName, studentId))
                if (response.isSuccessful) {
                    val keypass = response.body()?.keypass.orEmpty()
                    if (keypass.isNotEmpty()) {
                        val prefs = getSharedPreferences("UserCredentials", MODE_PRIVATE)
                        with(prefs.edit()) {
                            putString("username", firstName)
                            putString("password", studentId)
                            putString("topic", keypass)
                            apply()
                        }
                        startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "Invalid credentials or no topic.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Network error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
