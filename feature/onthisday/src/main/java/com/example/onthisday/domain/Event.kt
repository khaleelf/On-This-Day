package com.example.onthisday.domain


data class Event(
    val title: String,
    val year: Int,
    val links: List<Url>,
)

@JvmInline
value class Url(val string: String)