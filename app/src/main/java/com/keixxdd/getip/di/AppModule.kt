package com.keixxdd.getip.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.keixxdd.getip.data.api.GetIpService
import com.keixxdd.getip.presentation.application.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val baseUrl = "http://awstest-balancer-1233234915.us-east-2.elb.amazonaws.com/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication = BaseApplication()

    @Singleton
    @Provides
    fun provideRetrofitService(): GetIpService{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GetIpService::class.java)
    }
}