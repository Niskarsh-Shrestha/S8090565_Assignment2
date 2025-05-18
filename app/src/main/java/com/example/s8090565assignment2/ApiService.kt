package com.example.s8090565assignment2.network

import com.example.s8090565assignment2.model.AuthRequest
import com.example.s8090565assignment2.model.AuthResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Sends a POST request to authenticate the user
    @POST("sydney/auth")
    suspend fun authenticate(@Body request: AuthRequest): Response<AuthResponse>

    // Sends a GET request to retrieve dashboard data based on the topic, first name, and student ID
    @GET("dashboard/{topic}")
    fun getDashboardData(
        @Path("topic") topic: String,               // Path parameter for topic
        @Query("firstName") firstName: String,      // Query parameter for user's first name
        @Query("studentID") studentID: String       // Query parameter for student ID
    ): retrofit2.Call<DashboardResponse>

    // Data class representing the response structure from the dashboard API
    data class DashboardResponse(
        val entities: List<Map<String, String>>     // List of key-value pairs from the dashboard
    )
}
