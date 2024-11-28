package com.example.onthisday.domain


data class HistoricEvent(
    val title: String,
    val year: Int,
    val links: List<Link>,
)

data class Link(val title: String, val url: Url)

@JvmInline
value class Url(val string: String)