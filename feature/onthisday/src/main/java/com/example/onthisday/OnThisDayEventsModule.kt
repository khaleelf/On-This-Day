package com.example.onthisday

import com.example.onthisday.data.OnThisDayEventService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object OnThisDayEventsModule {

    @Provides
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    fun provideRetrofit(
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://byabbe.se/")
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json; charset=UTF8")))
            .build()
    }

    @Provides
    fun provideOnThisDayService(
        retrofit: Retrofit,
    ): OnThisDayEventService {
        return retrofit.create(OnThisDayEventService::class.java)
    }
}
