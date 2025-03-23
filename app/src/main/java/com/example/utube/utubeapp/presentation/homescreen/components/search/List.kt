package com.example.utube.utubeapp.presentation.homescreen.components.search

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.domain.model.SearchResult

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun List(
    searchResult: List<SearchResult>?,
    onSearchResultClick: (SearchResult) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
) {
    Log.d("ListKt", "List: searchResult = $searchResult")
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (searchResult != null) {

            items(
                items = searchResult,
            ) { searchResult ->
                Log.d("ListKt", "List: result = $searchResult")
                SearchItem(
                    searchResult = searchResult,
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = { onSearchResultClick(searchResult) }
                )
            }
        }else {
            item {
                Text(text = "No results found")
            }
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteList(
    navController: NavController,
    searchResult: List<FavoriteVideo>?,
    onSearchResultClick: (FavoriteVideo) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
) {

    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (searchResult != null) {

            items(
                items = searchResult,
            ) { searchResult ->
                Log.d("ListKt", "List: result = $searchResult")
                FavoriteItem(
                    favoriteVideo = searchResult,
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        onSearchResultClick(searchResult)
                        navController.navigate("videodetailscreen/${searchResult.id}")
                    }
                )
            }
        }else {
            item {
                Text(text = "No results found")
            }
        }

    }
}



