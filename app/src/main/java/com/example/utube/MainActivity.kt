package com.example.utube

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.utube.core.auth.AuthViewModel
import com.example.utube.core.navigation.MyAppNavigation
import com.example.utube.ui.theme.UtubeTheme
import com.example.utube.utubeapp.presentation.homescreen.components.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.compose.koinViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            UtubeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyAppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }
        }
    }
}

