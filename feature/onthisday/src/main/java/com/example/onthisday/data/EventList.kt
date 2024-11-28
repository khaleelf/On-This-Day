package com.example.onthisday.data

import kotlinx.serialization.Serializable

@Serializable
data class EventList(
    val wikipedia: String,
    val date: String,
)