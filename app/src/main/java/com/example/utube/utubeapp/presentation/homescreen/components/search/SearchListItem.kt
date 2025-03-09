package com.example.utube.utubeapp.presentation.homescreen.components.search

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.utube.core.presentation.util.formatIso8601Time
import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.domain.model.SearchResult

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchItem(
    searchResult: SearchResult,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val snippet = searchResult.snippet
    Surface(
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            AsyncImage(
                model = snippet.thumbnails.default.url,
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .size(120.dp, 80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title, Channel Title, and Publish Time
            Column {
                Text(
                    text = snippet.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = snippet.channelTitle,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Uploaded ${formatIso8601Time(snippet.publishedAt)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteItem(
    favoriteVideo: FavoriteVideo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(vertical = 16.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            AsyncImage(
                model = favoriteVideo.thumbnails,
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .size(120.dp, 80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title, Channel Title, and Publish Time
            Column {
                Text(
                    text = favoriteVideo.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = favoriteVideo.channelTitle,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Uploaded ${formatIso8601Time(favoriteVideo.publishedAt)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}