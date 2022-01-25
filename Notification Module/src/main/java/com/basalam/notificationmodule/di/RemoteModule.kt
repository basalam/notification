package com.basalam.notificationmodule.di

import com.basalam.notificationmodule.data.service.RemoteHelper
import com.basalam.notificationmodule.data.service.RemoteHelperImpl
import com.basalam.notificationmodule.data.service.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideApiService(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://automation.basalam.com")
        .build()

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiHelper(remoteHelperImpl: RemoteHelperImpl): RemoteHelper = remoteHelperImpl
}