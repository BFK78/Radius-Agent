package com.example.radiusagent.di

import com.example.radiusagent.data.repository.FacilitiesRepositoryImplementation
import com.example.radiusagent.domain.repository.FacilitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindingModule {

    @Binds
    fun bindFacilitiesRepository(
        facilitiesRepository: FacilitiesRepositoryImplementation
    ): FacilitiesRepository

}