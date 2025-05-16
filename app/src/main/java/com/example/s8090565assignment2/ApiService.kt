package com.example.s8090565assignment2.network

import com.example.s8090565assignment2.model.AuthRequest
import com.example.s8090565assignment2.model.AuthResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("sydney/auth")
    suspend fun authenticate(@Body request: AuthRequest): Response<AuthResponse>

    @GET("dashboard/{topic}")
    fun getDashboardData(
        @Path("topic") topic: String,
        @Query("firstName") firstName: String,
        @Query("studentID") studentID: String
    ): retrofit2.Call<DashboardResponse>

    data class DashboardResponse(
        val entities: List<Map<String, String>>
    )
}
