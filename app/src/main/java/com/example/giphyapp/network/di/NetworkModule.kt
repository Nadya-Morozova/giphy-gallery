package com.example.giphyapp.network.di

import android.content.Context
import com.example.giphyapp.network.api.GipHyApi
import com.example.giphyapp.network.interceptor.GipHyInterceptor
import com.example.giphyapp.utils.ConnectivityTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val URL = "https://api.giphy.com/v1/gifs/"

    @Provides
    fun provideConnectivityTracker(@ApplicationContext context: Context): ConnectivityTracker {
        return ConnectivityTracker(context)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(GipHyInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideGipHyApi(retrofit: Retrofit): GipHyApi {
        return retrofit.create(GipHyApi::class.java)
    }

}