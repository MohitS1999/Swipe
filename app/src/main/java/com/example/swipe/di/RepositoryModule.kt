package com.example.swipe.di

import com.example.swipe.api.SwipeApi
import com.example.swipe.repository.ApiRepository
import com.example.swipe.repository.ApiRepositoryImpl
import com.example.swipe.repository.PostRepositoryImpl
import com.example.swipe.repository.PostRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesApiRepository(
        api: SwipeApi
    ) : ApiRepository{
        return ApiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesPostRepository(
        api: SwipeApi
    ) : PostRespository {
        return PostRepositoryImpl(api)
    }
}