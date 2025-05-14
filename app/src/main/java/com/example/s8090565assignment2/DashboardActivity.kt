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
            intent.putExtra("dish", dish) // Pass the selected dish to DetailActivity
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Get firstName and studentID from intent
        val firstName = intent.getStringExtra("firstName")?.trim() ?: ""
        val studentID = intent.getStringExtra("studentID")?.trim() ?: ""

        fetchDishes(firstName, studentID)
    }

    private fun fetchDishes(firstName: String, studentID: String) {
        val url = "https://nit3213api.onrender.com/dashboard/food?firstName=$firstName&studentID=$studentID"

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
                    val jsonResponse = JSONObject(responseBody)
                    val entities = jsonResponse.getJSONArray("entities")
                    val dishList = mutableListOf<Dish>()

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
                            Toast.makeText(this@DashboardActivity, "No dishes found", Toast.LENGTH_SHORT).show()
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
