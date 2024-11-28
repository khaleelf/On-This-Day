package com.example.onthisday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.onthisday.presentation.EventsScreen
import com.example.onthisday.presentation.OnThisDayViewModel
import com.example.onthisday.presentation.theme.OnThisDayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val onThisDayViewModel: OnThisDayViewModel by viewModels()

        setContent {
            OnThisDayTheme {
                val uiState by onThisDayViewModel.uiState.collectAsState()
                EventsScreen(uiState = uiState)
            }
        }
    }
}
