package dev.mayutama.project.eventapp.data.remote.retrofit

import dev.mayutama.project.eventapp.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {
    val getLoggingInterceptor: HttpLoggingInterceptor = if(BuildConfig.DEBUG){
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
}