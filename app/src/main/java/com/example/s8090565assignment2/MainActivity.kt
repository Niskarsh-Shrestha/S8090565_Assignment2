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

        // Apply window insets to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        etFirstName = findViewById(R.id.etFirstName)
        etStudentId = findViewById(R.id.etStudentId)
        btnLogin = findViewById(R.id.btnLogin)

        // Set onClickListener for the login button
        btnLogin.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()

            // Check if both fields are filled
            if (firstName.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show()
            } else {
                btnLogin.isEnabled = true;
                // Authenticate the user
                authenticateUser(firstName, studentId)
            }
        }
    }

    private fun authenticateUser(firstName: String, studentId: String) {
        // Construct the URL with the provided firstName and studentId
        val url = "https://nit3213api.onrender.com/dashboard/food?firstName=$firstName&studentID=$studentId"

        // Create the HTTP request
        val request = Request.Builder()
            .url(url)
            .build()

        // Execute the HTTP request asynchronously
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() // Log the error for debugging
                runOnUiThread {
                    // Show a network error message
                    Toast.makeText(this@MainActivity, "Network error, please try again.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // Get the response body as a string
                val responseBody = response.body?.string()

                // Check if the response is successful
                if (response.isSuccessful && responseBody != null) {
                    try {
                        // Parse the JSON response
                        val json = JSONObject(responseBody)
                        val entities = json.optJSONArray("entities")

                        // Check if there are any entities in the response
                        if (entities != null && entities.length() > 0) {
                            runOnUiThread {
                                // If there are entities, proceed to the DashboardActivity
                                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                intent.putExtra("firstName", firstName)
                                intent.putExtra("studentID", studentId)
                                startActivity(intent) // Start the new activity
                                finish() // Close the current activity
                            }
                        } else {
                            // If no entities were found, show a message
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Invalid credentials or no data found", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace() // Log the error for debugging
                        runOnUiThread {
                            // Show a message if there was an error parsing the data
                            Toast.makeText(this@MainActivity, "Error parsing data", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // If the response was not successful, show an error message
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Login failed: Unauthorized or bad response", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
