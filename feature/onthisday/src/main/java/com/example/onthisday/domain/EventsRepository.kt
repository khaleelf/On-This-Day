package com.example.onthisday.domain

import android.util.Log
import com.example.onthisday.data.OnThisDayEventService
import com.example.onthisday.data.getEvents
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val onThisDayEventService: OnThisDayEventService,
) {

    suspend fun getEvents(date: Date): List<Event> {
        val wikipedia = onThisDayEventService.getEvents(date).body()?.wikipedia
        Log.d(TAG, "getEvents: $wikipedia")
        return emptyList()
    }

    companion object {
        private const val TAG = "EventsRepository"
    }
}