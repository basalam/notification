package com.basalam.notificationmodule.di

import com.basalam.notificationmodule.repository.NotificationRepository
import com.basalam.notificationmodule.repository.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideRepository(
        repo: NotificationRepositoryImpl
    ): NotificationRepository = repo
}