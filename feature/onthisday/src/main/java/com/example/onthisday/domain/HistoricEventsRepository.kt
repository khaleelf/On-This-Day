package com.example.onthisday.domain

import android.util.Log
import com.example.onthisday.data.OnThisDayEventService
import com.example.onthisday.data.getEvents
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class HistoricEventsRepository @Inject constructor(
    private val onThisDayEventService: OnThisDayEventService,
) : EventsRepository {

    override suspend fun getEventsResult(date: Date): EventsResult {
        return fromNetwork(date)
    }

    private suspend fun fromNetwork(date: Date): EventsResult {
        return try {
            val eventsFromNetwork = onThisDayEventService.getEvents(date)
            val historicEvents = eventsFromNetwork.toHistoricEvents()
            EventsResult.Success(historicEvents)
        } catch (httpException: HttpException) {
            Log.e(TAG, "Error from network.", httpException)
            EventsResult.Error("Error from network: ${httpException.code()}")
        } catch (unknownHostException: UnknownHostException) {
            Log.e(TAG, "", unknownHostException)
            EventsResult.Error("Check network on device.")
        } catch (exception: Exception) {
            Log.e(TAG, "Unknown failure while attempting network request.", exception)
            EventsResult.Error("Unknown Error")
        }
    }

    companion object {
        private const val TAG = "EventsRepository"
    }
}