package com.example.onthisday.data

import kotlinx.serialization.Serializable

@Serializable
data class Events(
    val wikipedia: String,
    val date: String,
    val events: List<PastEvent>,
)

fun <T> Events.map(f: (Events) -> T): T = f(this)

@Serializable
data class PastEvent(
    val year: String,
    val description: String,
    val wikipedia: List<WikipediaLink>
)

@Serializable
data class WikipediaLink(
    val title: String,
    val wikipedia: String,
)