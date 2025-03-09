package com.example.utube.utubeapp.presentation.videodetail.component


import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YoutubePlayer(modifier: Modifier = Modifier, ytVideoId: String, lifecycleOwner: LifecycleOwner) {

    AndroidView(
        modifier = modifier,
        factory = {
            YouTubePlayerView(context = it).apply{
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(ytVideoId, 0f)
                    }

                })


            }
        }
    )
}