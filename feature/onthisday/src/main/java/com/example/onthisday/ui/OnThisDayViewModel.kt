package com.example.onthisday.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class OnThisDayViewModel : ViewModel() {

    init {
        viewModelScope.launch {
            Log.d(TAG, dummyData()!!)
        }
    }

    companion object {
        private const val TAG = "OnThisDayViewModel"
    }

    suspend fun dummyData() = service.getEvents("11", "27").body()?.wikipedia
}

private val json = Json { ignoreUnknownKeys = true }

val retrofit = Retrofit.Builder()
    .baseUrl("https://byabbe.se/")
    .addConverterFactory(
        json.asConverterFactory(
                MediaType.get("application/json; charset=UTF8")
            )
    )
    .build()

interface OnThisDayService {
    @GET("on-this-day/{month}/{day}/events.json")
    suspend fun getEvents(
        @Path("month") month: String,
        @Path("day") day: String
    ): Response<EventList>
}

val service = retrofit.create(OnThisDayService::class.java)

@Serializable
data class EventList(
    val wikipedia: String,
    val date: String,
)