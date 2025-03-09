package com.example.utube.utubeapp.presentation.homescreen

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.utube.utubeapp.presentation.homescreen.components.search.List
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.utube.core.auth.AuthState
import com.example.utube.core.auth.AuthViewModel
import com.example.utube.core.navigation.Route
import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.presentation.homescreen.components.search.FavoriteList
import com.example.utube.utubeapp.presentation.homescreen.components.search.SearchAction
import com.example.utube.utubeapp.presentation.homescreen.components.search.SearchViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate(Route.LoginScreen)
            else -> Unit
        }
    }

    var searchText by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Playlist", "Favorites")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        // Top Bar with Sign Out Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.headlineMedium
            )

            // Search Bar
            IconButton(onClick = {navController.navigate("searchscreen")}) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search for videos"
                )
            }




            IconButton(onClick =  {authViewModel.signout()}) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Sign Out"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tab Row
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> PlaylistScreen()
            1 -> FavoritesScreen(state.favoriteVideos)
        }
    }
}

@Composable
fun PlaylistScreen() {
    Text(text = "Playlist Screen Content", style = MaterialTheme.typography.bodyLarge)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoritesScreen(
    favoriteVideos: List<FavoriteVideo>
    ) {
    val searchResultsListState: LazyListState = rememberLazyListState()
    FavoriteList(
        searchResult = favoriteVideos,
        onSearchResultClick = {

        },
        modifier = Modifier.fillMaxSize(),
        scrollState = searchResultsListState
    )
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen()
//}

//@Composable
//fun HomeScreen(
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    authViewModel: AuthViewModel
//){
//    val authState = authViewModel.authState.observeAsState()
//
//    LaunchedEffect(authState.value) {
//        when(authState.value){
//            is AuthState.Unauthenticated -> navController.navigate("login")
//            else -> Unit
//        }
//    }
//
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Home Page", fontSize = 32.sp)
//
//        TextButton(onClick = {
//            authViewModel.signout()
//        }) {
//            Text(text = "Sign out")
//        }
//    }
//}