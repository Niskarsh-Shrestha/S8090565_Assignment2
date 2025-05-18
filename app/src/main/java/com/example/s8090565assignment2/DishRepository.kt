package com.example.s8090565assignment2

import javax.inject.Inject

// Repository class for Dish-related data operations
// Injected via Hilt for dependency management
class DishRepository @Inject constructor() {

    // Simple method to verify repository injection
    fun logRepositoryUsed(): String {
        return "DishRepository injected successfully using Hilt!"
    }
}
