package com.example.onthisday.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onthisday.domain.HistoricEvent

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    uiState: EventsUiState,
) = Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    when (uiState) {
        is EventsUiState.Error -> ErrorScreen(
            modifier = modifier.padding(innerPadding),
            info = uiState.reason
        )

        is EventsUiState.Display -> DisplayScreen(
            modifier = modifier.padding(innerPadding),
            uiState.historicEvents
        )

        EventsUiState.Loading -> LoadingScreen(modifier = modifier.padding(innerPadding))
    }
}

@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier,
    info: String,
) = Box(modifier.fillMaxSize()) {
    Text(text = info, modifier = Modifier.align(Alignment.Center), fontSize = 25.sp)
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) = Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    CircularProgressIndicator(Modifier.size(120.dp).padding(16.dp))
    Text("Loading...", fontSize = 25.sp)
}

@Composable
private fun DisplayScreen(
    modifier: Modifier = Modifier,
    events: List<HistoricEventGroup>,
) {
    LazyColumn(modifier) {
        items(items = events, key = HistoricEventGroup::date) { group ->
            Column {
                Text(text = group.date, Modifier.padding(12.dp))
                for (event in group.events) {
                    HistoricalEvent(historicEvent = event)
                }
            }
        }
    }
}

@Composable
private fun HistoricalEvent(modifier: Modifier = Modifier, historicEvent: HistoricEvent) =
    Card(modifier = modifier.padding(12.dp)) {
        Column(Modifier.fillMaxSize()) {
            Text(text = historicEvent.title)
            for (link in historicEvent.links) {
                Text(text = link.title)
            }
        }
    }