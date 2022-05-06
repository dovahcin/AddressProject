package com.example.address.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ApiInterceptor(private val credentials: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original =chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", credentials)
            .url(originalHttpUrl.newBuilder().build())
        return chain
            .proceed(requestBuilder.build())
    }
}