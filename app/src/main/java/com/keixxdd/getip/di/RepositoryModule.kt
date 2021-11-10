package com.keixxdd.getip.di

import com.keixxdd.getip.data.api.GetIpService
import com.keixxdd.getip.data.repository.DefaultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideDefaultRepository(service: GetIpService): DefaultRepository = DefaultRepository(service)

}