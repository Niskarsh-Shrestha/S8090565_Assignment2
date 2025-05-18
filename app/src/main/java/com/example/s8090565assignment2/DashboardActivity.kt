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
import com.example.s8090565assignment2.network.ApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    // Injecting the ApiService using Hilt for making API calls
    @Inject lateinit var apiService: ApiService

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DishAdapter

    // Accessing stored user credentials and topic using SharedPreferences
    private val sharedPreferences by lazy { getSharedPreferences("UserCredentials", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Logging out: clearing activity stack and returning to login screen
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Setting up RecyclerView with adapter
        recyclerView = findViewById(R.id.entityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DishAdapter(emptyList()) { dish ->
            // Navigating to DetailActivity on item click
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish", dish)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Retrieving user data and topic from SharedPreferences
        val firstName = sharedPreferences.getString("username", "") ?: ""
        val studentID = sharedPreferences.getString("password", "") ?: ""
        val topic = sharedPreferences.getString("topic", "") ?: ""

        // Checking if data is available before making the API call
        if (firstName.isNotEmpty() && studentID.isNotEmpty() && topic.isNotEmpty()) {
            fetchDishes(firstName, studentID, topic)
        } else {
            Toast.makeText(this, "Missing credentials or topic", Toast.LENGTH_LONG).show()
        }
    }

    // Fetches data from the API and updates the RecyclerView
    private fun fetchDishes(firstName: String, studentID: String, topic: String) {
        apiService.getDashboardData(topic, firstName, studentID).enqueue(object : retrofit2.Callback<ApiService.DashboardResponse> {
            override fun onResponse(
                call: retrofit2.Call<ApiService.DashboardResponse>,
                response: retrofit2.Response<ApiService.DashboardResponse>
            ) {
                if (response.isSuccessful) {
                    val dashboardResponse = response.body()
                    val entities = dashboardResponse?.entities ?: emptyList()

                    // Mapping API data to Dish objects
                    val dishList = entities.map { entity -> Dish(entity) }

                    adapter.updateDishes(dishList)

                    if (dishList.isEmpty()) {
                        Toast.makeText(this@DashboardActivity, "No data found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@DashboardActivity, "Error: Could not retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<ApiService.DashboardResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
