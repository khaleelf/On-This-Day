@file:OptIn(ExperimentalSerializationApi::class)

package com.example.onthisday.domain

import android.util.Log
import com.example.onthisday.data.OnThisDayEventService
import com.example.onthisday.data.getEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class HistoricEventsRepository @Inject constructor(
    private val onThisDayEventService: OnThisDayEventService,
) : EventsRepository {

    override fun getEvents(date: Date): Flow<EventsResult> = flow {
        val eventsFromNetwork = fromNetwork(date)
        emit(eventsFromNetwork)
    }

    private suspend fun fromNetwork(date: Date): EventsResult {
        return try {
            Log.d(TAG, "fromNetwork: $date")
            val eventsFromNetwork = onThisDayEventService.getEvents(date)
            val historicEvents = eventsFromNetwork.toHistoricEvents()
            EventsResult.Success(historicEvents)
        } catch (httpException: HttpException) {
            Log.e(TAG, "Error from network.", httpException)
            EventsResult.Error("Error from network: ${httpException.code()}")
        } catch (unknownHostException: UnknownHostException) {
            Log.e(TAG, "", unknownHostException)
            EventsResult.Error("Check network on device.")
        } catch (missingFieldException: MissingFieldException) {
            Log.e(TAG, "Failed Serializing deserializing json, check the field names", missingFieldException)
            EventsResult.Error("Error while reading network request.")
        } catch (exception: Exception) {
            Log.e(TAG, "Unknown failure while attempting network request.", exception)
            EventsResult.Error("Unknown Error.")
        }
    }

    companion object {
        private const val TAG = "EventsRepository"
    }
}