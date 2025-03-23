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
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.RemoveRedEye
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
import androidx.compose.ui.text.style.TextOverflow
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
                .background(MaterialTheme.colorScheme.onBackground)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(15.dp))
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
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Views, Likes, Comments
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Likes",
                                tint = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = formatNumber(state.video.statistics.likeCount.toLong()),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Filled.RemoveRedEye,
                                contentDescription = "Views",
                                tint = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = formatNumber(state.video.statistics.viewCount.toLong()),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Filled.Comment,
                                contentDescription = "comment",
                                tint = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = formatNumber(state.video.statistics.commentCount.toLong()),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }


                    }


                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = state.video.snippet.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(15.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    IconButton(onClick = {onAction(VideoDetailAction.OnFavoriteClick)}) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Add to Favorites",
                            tint = if (state.isFavorite) Color.Red else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                }


            }
        }
    }
}

fun formatNumber(number: Long): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
}

