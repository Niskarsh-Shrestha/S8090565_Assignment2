package com.example.s8090565assignment2

import Dish
import DishAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    @Inject lateinit var okHttpClient: OkHttpClient

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DishAdapter

    private val sharedPreferences by lazy { getSharedPreferences("UserCredentials", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.entityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DishAdapter(emptyList()) { dish ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish", dish)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val firstName = sharedPreferences.getString("username", "") ?: ""
        val studentID = sharedPreferences.getString("password", "") ?: ""
        val topic = sharedPreferences.getString("topic", "") ?: ""

        if (firstName.isNotEmpty() && studentID.isNotEmpty() && topic.isNotEmpty()) {
            fetchDishes(firstName, studentID, topic)
        } else {
            Toast.makeText(this, "Missing credentials or topic", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchDishes(firstName: String, studentID: String, topic: String) {
        val url =
            "https://nit3213api.onrender.com/dashboard/$topic?firstName=$firstName&studentID=$studentID"
        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Failed to fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    try {
                        val jsonResponse = JSONObject(responseBody)
                        val entities = jsonResponse.getJSONArray("entities")
                        val dishList = mutableListOf<Dish>()

                        for (i in 0 until entities.length()) {
                            val item = entities.getJSONObject(i)
                            val keys = item.keys()

                            val fields = mutableMapOf<String, String>()
                            while (keys.hasNext()) {
                                val key = keys.next()
                                fields[key] = item.optString(key)
                            }

                            dishList.add(Dish(fields))
                        }

                        runOnUiThread {
                            adapter.updateDishes(dishList)
                            if (dishList.isEmpty()) {
                                Toast.makeText(
                                    this@DashboardActivity,
                                    "No data found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(
                                this@DashboardActivity,
                                "Error processing data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@DashboardActivity,
                            "Error: Could not retrieve data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}
