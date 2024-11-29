package com.example.onthisday.presentation

import com.example.onthisday.domain.Date
import com.example.onthisday.domain.EventsRepository
import com.example.onthisday.domain.EventsResult
import com.example.onthisday.domain.HistoricEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnThisDayViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val events = listOf(
        HistoricEvent("title", 1, emptyList()),
        HistoricEvent("title2", 2, emptyList()),
        HistoricEvent("title3", 3, emptyList()),
    )

    private val hotRepository = object : EventsRepository {
        private val flow = MutableSharedFlow<EventsResult>()
        override fun getEvents(date: Date): Flow<EventsResult> = flow
        suspend fun emit(result: EventsResult) = flow.emit(result)
    }

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Initial value is loading`(): Unit = runTest {
        val viewModel = OnThisDayViewModel(hotRepository)
        val uiState = viewModel.uiState.first()
        assertEquals(EventsUiState.Loading, uiState)
    }

    @Test
    fun `Events are emitted in descending order for the UI`() = runTest {
        val viewModel = OnThisDayViewModel(hotRepository)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }
        hotRepository.emit(EventsResult.Success(events))
        val expected = EventsUiState.Display(
            listOf(
                HistoricEventGroup("3", listOf(events[2])),
                HistoricEventGroup("2", listOf(events[1])),
                HistoricEventGroup("1", listOf(events[0]))
            )
        )
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `Events with the same date are grouped together`() = runTest {
        val viewModel = OnThisDayViewModel(hotRepository)
        val groupedEvents = listOf(
            HistoricEvent("title", 1, emptyList()),
            HistoricEvent("title2", 1, emptyList()),
            HistoricEvent("title3", 3, emptyList()),
        )
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }
        hotRepository.emit(EventsResult.Success(groupedEvents))
        val expected = EventsUiState.Display(
            listOf(
                HistoricEventGroup("3", listOf(groupedEvents[2])),
                HistoricEventGroup("1", listOf(groupedEvents[0], groupedEvents[1]))
            )
        )
        assertEquals(expected, viewModel.uiState.value)
    }

    @Test
    fun `Error states from repo map to UI error`() = runTest {
        val viewModel = OnThisDayViewModel(hotRepository)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }
        hotRepository.emit(EventsResult.Error("fake test error"))
        val expected = EventsUiState.Error("fake test error")
        assertEquals(expected, viewModel.uiState.value)
    }
}
