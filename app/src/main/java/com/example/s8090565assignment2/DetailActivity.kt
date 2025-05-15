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

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener { finish() }

        val dish = intent.getParcelableExtra<Dish>("dish")

        if (dish != null) {
            val descriptionText: TextView = findViewById(R.id.descriptionText)
            val builder = StringBuilder()
            for ((key, value) in dish.fields) {
                builder.append("$key: $value\n")
            }
            descriptionText.text = builder.toString()
        } else {
            Toast.makeText(this, "Failed to load details", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
