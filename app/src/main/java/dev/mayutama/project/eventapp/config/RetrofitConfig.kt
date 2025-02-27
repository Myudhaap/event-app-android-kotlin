package dev.mayutama.project.eventapp.config

import dev.mayutama.project.eventapp.data.remote.retrofit.LoggingInterceptor
import dev.mayutama.project.eventapp.data.remote.service.EventService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor.getLoggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://event-api.dicoding.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val eventService: EventService = retrofit.create(EventService::class.java)
}