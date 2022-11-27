package com.example.chililabstest.data

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private val apiKey = "ptzwgQgWPLNiibJPCpgxeG7wqS1RLzBy"

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", apiKey).build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}