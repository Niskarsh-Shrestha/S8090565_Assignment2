package com.example.s8090565assignment2

import Dish
import DishAdapter
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard) // Ensure the correct layout is set

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.entityRecyclerView)  // Corrected to match the ID in the XML
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DishAdapter(emptyList()) // Initially empty list
        recyclerView.adapter = adapter

        // Fetch dishes from the API
        fetchDishes()
    }

    private fun fetchDishes() {
        val request = Request.Builder()
            .url("https://nit3213api.onrender.com/dashboard/food") // Replace with your actual API URL for dishes
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    Log.d("API_RESPONSE", "Code: ${response.code}, Body: $responseBody")
                    // Parse JSON response
                    val jsonResponse = JSONObject(responseBody)
                    val entities = jsonResponse.getJSONArray("entities")
                    val dishes = mutableListOf<Dish>()

                    for (i in 0 until entities.length()) {
                        val dish = entities.getJSONObject(i)
                        val dishName = dish.getString("dishName")
                        val origin = dish.getString("origin")
                        val mainIngredient = dish.getString("mainIngredient")
                        val mealType = dish.getString("mealType")
                        val description = dish.getString("description")

                        dishes.add(Dish(dishName, origin, mainIngredient, mealType, description))
                    }

                    // Update RecyclerView with fetched dishes
                    runOnUiThread {
                        adapter.updateDishes(dishes)
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
