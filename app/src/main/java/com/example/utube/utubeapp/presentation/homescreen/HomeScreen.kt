package com.example.utube.utubeapp.presentation.homescreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        // Top Bar with Sign Out Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Search Bar
            IconButton(onClick = {navController.navigate("searchscreen")}) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search for videos",
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )
            }




            IconButton(onClick =  {authViewModel.signout()}) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Sign Out",
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
        Text(
            text = "   Favourite Videos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        FavoritesScreen(
            favoriteVideos = state.favoriteVideos,
            onAction = { action ->
                when (action) {
                    is SearchAction.OnFavouriteVideoClick ->  (action.video)
                    else -> Unit
                }
                viewModel.onAction(action)
            },
            navController = navController
            )

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoritesScreen(
    favoriteVideos: List<FavoriteVideo>,
    onAction: (SearchAction) -> Unit,
    navController: NavController,
    ) {
    val searchResultsListState: LazyListState = rememberLazyListState()
    FavoriteList(
        searchResult = favoriteVideos,
        onSearchResultClick = {
            onAction(SearchAction.OnFavouriteVideoClick(it))
        },
        modifier = Modifier.fillMaxSize(),
        scrollState = searchResultsListState,
        navController = navController,
    )
}

