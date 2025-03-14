package dev.mayutama.project.eventapp.data.remote.service

import dev.mayutama.project.eventapp.data.remote.response.EventDetailResponse
import dev.mayutama.project.eventapp.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface EventService {
    @GET("events")
    suspend fun getAllEvent(
        @QueryMap queryParams: Map<String, String>
    ): EventResponse

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventDetailResponse
}