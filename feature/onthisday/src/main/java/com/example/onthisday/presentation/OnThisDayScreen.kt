package com.example.onthisday.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.onthisday.R
import com.example.onthisday.domain.HistoricEvent

@Composable
fun OnThisDayScreen(
    modifier: Modifier = Modifier,
    uiState: OnThisDayUiState,
    onClick: (String) -> Unit,
) = Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    when (uiState) {
        is OnThisDayUiState.Error -> ErrorScreen(
            modifier = modifier.padding(innerPadding),
            info = uiState.reason
        )

        is OnThisDayUiState.Display -> DisplayScreen(
            modifier = modifier.padding(innerPadding),
            events = uiState.historicEvents,
            onClick = onClick,
        )

        OnThisDayUiState.Loading -> LoadingScreen(modifier = modifier.padding(innerPadding))
    }
}

@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier,
    info: String,
) = Box(modifier.fillMaxSize()) {
    Text(
        text = info,
        modifier = Modifier.align(Alignment.Center),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) = Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(64.dp)
            .padding(bottom = 16.dp),
        strokeWidth = 4.dp
    )
    Text(
        text = "Loading...",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun DisplayScreen(
    modifier: Modifier = Modifier,
    events: List<HistoricEventGroup>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items = events, key = HistoricEventGroup::date) { group ->
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = group.date,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                for (event in group.events) {
                    HistoricalEvent(historicEvent = event, onClick = onClick)
                }
            }
        }
    }
}

@Composable
private fun HistoricalEvent(
    modifier: Modifier = Modifier,
    historicEvent: HistoricEvent,
    onClick: (String) -> Unit
) = Card(
    modifier = modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxSize(),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = historicEvent.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        for (link in historicEvent.links) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = link.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_open_in_new_24),
                    contentDescription = "External Link",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                        .clickable { onClick(link.url.value) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}