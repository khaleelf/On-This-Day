package com.example.onthisday.domain

import com.example.onthisday.data.Events
import com.example.onthisday.data.PastEvent
import com.example.onthisday.data.map

fun Events.toHistoricEvents() : List<HistoricEvent> = map { event ->
    event.pastEvents.map { pastEvent: PastEvent ->
        HistoricEvent(
            title = pastEvent.description,
            year = pastEvent.year.toInt(), // TODO this is a little dangerous.
            links = pastEvent.wikipedia.map { link ->
                Link(title = link.title, url = Url(link.wikipedia))
            }
        )
    }
}