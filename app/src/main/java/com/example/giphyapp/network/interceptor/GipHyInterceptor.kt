package com.example.giphyapp.network.interceptor

import com.example.giphyapp.BuildConfig
import com.example.giphyapp.network.di.NetworkModule
import okhttp3.Interceptor
import okhttp3.Response

class GipHyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl = request.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.GIF_API_KEY)
            .addQueryParameter("rating","g")
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}