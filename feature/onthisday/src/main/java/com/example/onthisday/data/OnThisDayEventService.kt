package com.example.onthisday.data

import com.example.onthisday.domain.Date
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface OnThisDayEventService {
    @GET("on-this-day/{month}/{day}/events.json")
    suspend fun getEvents(
        @Path("day") day: String,
        @Path("month") month: String,
    ): Response<EventList>
}

suspend fun OnThisDayEventService.getEvents(date: Date): Response<EventList> {
   return getEvents(date.day.toString(), date.month.toString())
}