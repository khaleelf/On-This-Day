package com.example.onthisday.domain

import com.example.onthisday.data.Events
import com.example.onthisday.data.PastEvent
import com.example.onthisday.data.WikipediaLink
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MappersTest {

    @Test
    fun `map Event from network to HistoricEvent`() {
        val events = Events(
            wikipedia = "wiki-url",
            date = "November 29",
            events = listOf(
                PastEvent(
                    year = "2005",
                    description = "some event",
                    wikipedia = listOf(
                        WikipediaLink("title to link", "link-url")
                    ),
                )
            )
        )
        val expected = listOf(
            HistoricEvent(
                "some event",
                2005,
                links = listOf(Link("title to link", Url("link-url")))
            )
        )
        assertEquals(expected, events.toHistoricEvents())
    }

    @Test
    fun `years with text are skipped`() {
        val events = Events(
            wikipedia = "wiki-url",
            date = "November 29",
            events = listOf(
                PastEvent(
                    year = "AD 2005",
                    description = "some event",
                    wikipedia = listOf(
                        WikipediaLink("title to link", "link-url")
                    ),
                )
            )
        )
        assertEquals(emptyList<HistoricEvent>(), events.toHistoricEvents())
    }
}