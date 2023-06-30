package com.example.radiusagent.di

import com.example.radiusagent.data.local.util.DatabaseUtils
import com.example.radiusagent.data.remote.api.FacilitiesApi
import com.example.radiusagent.data.remote.util.NetworkingConstants.BASE_URL
import com.example.radiusagent.domain.entity.ExclusionEntity
import com.example.radiusagent.domain.entity.ExclusionsEntity
import com.example.radiusagent.domain.entity.FacilityEntity
import com.example.radiusagent.domain.entity.OptionEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    /*
    * Creating OkHTTP client for retrofit.
    */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    /*
    * Creating retrofit instance using builder pattern
    * for calling API
    */
    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*
    * Creating an Instance for Facilities API
    * */
    @Provides
    @Singleton
    fun provideFacilitiesAPI(
        retrofit: Retrofit
    ): FacilitiesApi {
        return retrofit.create(FacilitiesApi::class.java)
    }


    /**
     * Providing Realm object for database operations.
     */
    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val configuration = RealmConfiguration.Builder(
            schema = setOf(
                FacilityEntity::class,
                OptionEntity::class,
                ExclusionEntity::class,
                ExclusionsEntity::class
            )
        )
            .name(DatabaseUtils.DATABASE_NAME)
            .build()

        return Realm.open(configuration = configuration)
    }

}