package com.example.onthisday.domain

import com.example.onthisday.data.Events
import com.example.onthisday.data.PastEvent
import com.example.onthisday.data.map

fun Events.toHistoricEvents() : List<HistoricEvent> = map { event ->
    event.events.mapNotNull { pastEvent: PastEvent ->
        runCatching {
            HistoricEvent(
                title = pastEvent.description,
                year = pastEvent.year.toInt(), // could throw NumberFormatException.
                links = pastEvent.wikipedia.map { link ->
                    Link(title = link.title, url = Url(link.wikipedia))
                }
            )
        }.getOrNull()
    }
}