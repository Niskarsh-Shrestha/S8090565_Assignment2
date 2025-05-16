package com.example.s8090565assignment2

import javax.inject.Inject

class DishRepository @Inject constructor() {
    fun logRepositoryUsed(): String {
        return "DishRepository injected successfully using Hilt!"
    }
}
