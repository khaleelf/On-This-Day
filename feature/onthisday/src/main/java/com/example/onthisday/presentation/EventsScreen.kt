package com.example.onthisday.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Text(text = info, modifier = Modifier.align(Alignment.Center))
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) = Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
) {
    CircularProgressIndicator()
    Text("Loading...")
}

@Composable
fun DisplayScreen(modifier: Modifier = Modifier, events: List<HistoricEvent>) {
    LazyColumn(modifier) {
        items(items = events, key = HistoricEvent::id) {
            Text(it.title)
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Greeting("Android")
//}