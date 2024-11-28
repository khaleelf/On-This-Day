package com.example.onthisday.domain

interface EventsRepository {
    suspend fun getEventsResult(date: Date): EventsResult
}
