package com.example.onthisday.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onthisday.domain.Date
import com.example.onthisday.domain.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnThisDayViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            dummyData()
        }
    }

    suspend fun dummyData() = eventsRepository.getEvents(Date(day = 28, month = 11))
}
