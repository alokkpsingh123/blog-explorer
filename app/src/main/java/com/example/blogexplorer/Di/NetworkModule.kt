package com.example.blogexplorer.Di

import com.example.blogexplorer.Retrofit.BlogAPI
import com.example.blogexplorer.Retrofit.BlogInterceptor
import com.example.blogexplorer.Utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient( blogInterceptor: BlogInterceptor): OkHttpClient{
        return OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(blogInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesBlogApi( retrofitBuilder: Retrofit.Builder , okHttpClient: OkHttpClient) : BlogAPI{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(BlogAPI::class.java)
    }
}