package com.example.s8090565assignment2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Activity representing the layout for a single dish item (usually for RecyclerView item layout)
class item_dish : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display to let content extend into system bars (status, navigation)
        enableEdgeToEdge()

        // Set the content view to the dish item layout
        setContentView(R.layout.activity_item_dish)

        // Adjust padding dynamically to avoid overlap with system bars (status, nav bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
