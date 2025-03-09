package com.example.utube.utubeapp.presentation.homescreen.components.search

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.utube.core.navigation.Route
import com.example.utube.utubeapp.domain.model.SearchResult


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreenRoot(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    onSearchResultClick: (SearchResult) -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(

        onAction = { action ->
            when (action) {
                is SearchAction.OnVideoClick -> onSearchResultClick(action.video)
                else -> Unit
            }
            viewModel.onAction(action)
        },
        state = state,
        navController = navController
    )

}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    navController: NavController,
    onAction: (SearchAction) -> Unit,
    state: SearchState,

) {

    Log.d("statkt", "selected:  ${state.theVideo}")

    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }
    val searchResultsListState = rememberLazyListState()


    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = {
                onAction(SearchAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(vertical = 36.dp, horizontal = 16.dp)
        )
        Spacer(Modifier.padding(vertical = 20.dp))
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.isLoading && state.searchQuery != "") {
                    CircularProgressIndicator()
                }  else{
                    List(
                        searchResult = state.searchResults,
                        onSearchResultClick = {
                            onAction(SearchAction.OnVideoClick(it))
                        },
                        modifier = Modifier.fillMaxSize(),
                        scrollState = searchResultsListState
                    )
                }
            }

        }
    }
}


