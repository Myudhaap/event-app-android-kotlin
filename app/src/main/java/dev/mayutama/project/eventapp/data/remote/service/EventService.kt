package dev.mayutama.project.eventapp.data.remote.service

import dev.mayutama.project.eventapp.data.remote.response.EventDetailResponse
import dev.mayutama.project.eventapp.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface EventService {
    @GET("events")
    suspend fun getAllEvent(
        @QueryMap queryParams: Map<String, String>
    ): EventResponse

    @GET("events")
    suspend fun getEventUpcoming(
        @Query("active") active: String = "-1",
        @Query("limit") limit: Int = 1
    ): EventResponse

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventDetailResponse
}