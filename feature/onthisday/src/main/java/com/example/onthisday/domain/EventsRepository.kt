package com.example.onthisday.domain

import kotlinx.coroutines.flow.Flow

interface EventsRepository {
    fun getEvents(date: Date): Flow<EventsResult>
}
