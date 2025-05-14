package com.example.s8090565assignment2

import Dish
import DishAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DishAdapter

    // Using SharedPreferences to store credentials securely
    private val sharedPreferences by lazy { getSharedPreferences("UserCredentials", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Logout button
        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.entityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an item click listener
        adapter = DishAdapter(emptyList()) { dish ->
            val intent = Intent(this@DashboardActivity, DetailActivity::class.java)
            intent.putExtra("dish", dish)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Fetch stored credentials and topic from SharedPreferences
        val firstName = sharedPreferences.getString("username", "") ?: ""
        val studentID = sharedPreferences.getString("password", "") ?: ""
        val topic = sharedPreferences.getString("topic", "food") ?: "food"

        fetchDishes(firstName, studentID, topic)
    }

    private fun fetchDishes(firstName: String, studentID: String, topic: String) {
        val url = "https://nit3213api.onrender.com/dashboard/$topic?firstName=$firstName&studentID=$studentID"

        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val entities = jsonResponse.getJSONArray("entities")
                        val dishList = mutableListOf<Dish>()

                        if (topic == "food") {
                            for (i in 0 until entities.length()) {
                                val dish = entities.getJSONObject(i)
                                val dishName = dish.getString("dishName")
                                val origin = dish.getString("origin")
                                val mainIngredient = dish.getString("mainIngredient")
                                val mealType = dish.getString("mealType")
                                val description = dish.getString("description")

                                dishList.add(Dish(dishName, origin, mainIngredient, mealType, description))
                            }

                            runOnUiThread {
                                adapter.updateDishes(dishList)
                                if (dishList.isEmpty()) {
                                    Toast.makeText(this@DashboardActivity, "No data found", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this@DashboardActivity,
                                    "This app only supports the 'food' topic. Your assigned topic is '$topic'.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@DashboardActivity, "Error processing data.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@DashboardActivity, "Error: Could not retrieve data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
