package com.example.s8090565assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var btnLogin: Button

    private val authUrl = "https://nit3213api.onrender.com/sydney/auth"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
        val client = OkHttpClient()
        val json = JSONObject()
        json.put("username", firstName)
        json.put("password", studentId)

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, json.toString())

        val request = Request.Builder()
            .url(authUrl)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Network error, please try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val keypass = jsonResponse.optString("keypass")

                        if (keypass.isNotEmpty()) {
                            runOnUiThread {
                                // Save credentials and topic to SharedPreferences
                                val sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE)
                                with(sharedPreferences.edit()) {
                                    putString("username", firstName)
                                    putString("password", studentId)
                                    putString("topic", keypass)
                                    apply()
                                }

                                // Launch dashboard
                                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        else {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Invalid credentials or no topic assigned.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Error parsing response.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}