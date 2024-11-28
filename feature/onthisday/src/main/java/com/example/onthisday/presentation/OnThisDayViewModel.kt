package com.example.onthisday.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onthisday.domain.Date
import com.example.onthisday.domain.EventsRepository
import com.example.onthisday.domain.EventsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class OnThisDayViewModel @Inject constructor(
    eventsRepository: EventsRepository,
) : ViewModel() {

    private val date by lazy {
        val localDate = LocalDate.now()
        Date(day = localDate.dayOfMonth, month = localDate.monthValue)
    }

    val uiState = eventsRepository
        .getEvents(date)
        .map(::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = EventsUiState.Loading
        )

    private fun toUiState(eventsResult: EventsResult) : EventsUiState = when(eventsResult) {
        is EventsResult.Error -> EventsUiState.Error(eventsResult.reason)
        is EventsResult.Success -> EventsUiState.Display(eventsResult.historicEvents)
    }
}
