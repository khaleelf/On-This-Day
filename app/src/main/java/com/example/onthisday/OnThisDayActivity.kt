package com.example.onthisday

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.onthisday.presentation.OnThisDayScreen
import com.example.onthisday.presentation.OnThisDayViewModel
import com.example.onthisday.presentation.theme.OnThisDayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnThisDayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val onThisDayViewModel: OnThisDayViewModel by viewModels()

        setContent {
            OnThisDayTheme {
                val uiState by onThisDayViewModel.uiState.collectAsState()
                OnThisDayScreen(
                    uiState = uiState,
                    onClick = { url -> openBrowser(context = this, url) })
            }
        }
    }

    private fun openBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}
