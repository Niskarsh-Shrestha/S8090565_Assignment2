package com.example.s8090565assignment2

import Dish
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s8090565assignment2.R

class DetailActivity : AppCompatActivity() {

    private lateinit var dishNameText: TextView
    private lateinit var originText: TextView
    private lateinit var mainIngredientText: TextView
    private lateinit var mealTypeText: TextView
    private lateinit var descriptionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Add inside onCreate() in DetailActivity.kt

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Closes DetailActivity and returns to DashboardActivity
        }


        // Initialize TextView references
        dishNameText = findViewById(R.id.dishNameText)
        originText = findViewById(R.id.originText)
        mainIngredientText = findViewById(R.id.mainIngredientText)
        mealTypeText = findViewById(R.id.mealTypeText)
        descriptionText = findViewById(R.id.descriptionText)

        // Get the Dish object passed from the adapter via Intent using Parcelable
        val dish = intent.getParcelableExtra<Dish>("dish")

        if (dish != null) {
            // Populate the TextViews with dish data
            dishNameText.text = "Dish Name: ${dish.dishName}"
            originText.text = "Origin: ${dish.origin}"
            mainIngredientText.text = "Main Ingredient: ${dish.mainIngredient}"
            mealTypeText.text = "Meal Type: ${dish.mealType}"
            descriptionText.text = "Description: ${dish.description}"
        } else {
            // Handle the error gracefully
            Toast.makeText(this, "Failed to load dish details", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
