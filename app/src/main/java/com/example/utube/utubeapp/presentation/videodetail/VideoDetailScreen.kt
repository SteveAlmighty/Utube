package com.example.utube.utubeapp.presentation.videodetail

import com.example.utube.utubeapp.domain.model.Video
import kotlin.text.toLong

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.utube.utubeapp.presentation.videodetail.component.YoutubePlayer
import java.text.NumberFormat
import java.util.Locale


@Composable
fun VideoDetailScreenRoot(
    viewModel: VideoDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,

) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    VideoDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is VideoDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },

    )
}


@Composable
fun VideoDetailScreen(
    state: VideoDetailState,
    onAction: (VideoDetailAction) -> Unit,

) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var isAddedToPlaylist by remember { mutableStateOf(false) }

    Log.d("vidkt", "List: vid = ${state.video }")

    if (state.video != null) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            IconButton(onClick = {onAction(VideoDetailAction.OnBackClick)}) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "go back"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Video Player
            YoutubePlayer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLandscape) configuration.screenHeightDp.dp else 250.dp),
                ytVideoId = state.video.id,
                lifecycleOwner = LocalLifecycleOwner.current
            )

            // Video Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = state.video.snippet.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Views, Likes, Comments
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = formatNumber(state.video.statistics.viewCount.toLong()) + " views",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row {
                            IconButton(onClick = {onAction(VideoDetailAction.OnFavoriteClick)}) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Add to Favorites",
                                    tint = if (state.isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = { isAddedToPlaylist = !isAddedToPlaylist }) {
                                Icon(
                                    imageVector = Icons.Filled.PlaylistAdd,
                                    contentDescription = "Add to Playlist",
                                    tint = if (isAddedToPlaylist) Color.Blue else MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }


                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = state.video.snippet.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Comments: ${state.video.statistics.commentCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

            }
        }
    }
}

fun formatNumber(number: Long): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
}

//@Preview(showBackground = true)
//@Composable
//fun VideoDetailScreenPreview() {
//    val videoItem = Video(
//        id = "videoId",
//        snippet = VideoSnippet(
//            publishedAt = "2023-01-01T00:00:00Z",
//            channelId = "channelId",
//            title = "Video Title",
//            description = "Video Description",
//            channelTitle = "Channel Title",
//        ),
//        statistics = VideoStatistics(
//            viewCount = "1000000",
//            likeCount = "10000",
//            favoriteCount = "100",
//            commentCount = "500"
//        ),
//        player = Player(embedHtml = "\u003ciframe width=\"480\" height=\"270\" src=\"//www.youtube.com/embed/ma67yOdMQfs\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen\u003e\u003c/iframe\u003e")
//    )
//    VideoDetailScreen(videoItem = videoItem)
//}