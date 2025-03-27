package com.joni.edumart.screens

import android.media.MediaMetadata
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@Composable
fun VideoPlayerScreen(modifier: Modifier = Modifier, videoUrl : String, videoTitle : String) {
    Column(modifier.fillMaxSize()) {
        VideoPlayer(
            mediaItems = listOf( VideoPlayerMediaItem.NetworkMediaItem(
                url = videoUrl,
                mediaMetadata = androidx.media3.common.MediaMetadata.Builder().setTitle(videoTitle).build(),

            )
            ),
            handleLifecycle = true,
            autoPlay = true,
            usePlayerController = true,
            enablePip = false,
            handleAudioFocus = true,
            controllerConfig = VideoPlayerControllerConfig(
                showSpeedAndPitchOverlay = false,
                showSubtitleButton = false,
                showCurrentTimeAndTotalTime = true,
                showBufferingProgress = false,
                showForwardIncrementButton = true,
                showBackwardIncrementButton = true,
                showBackTrackButton = true,
                showNextTrackButton = true,
                showRepeatModeButton = true,
                controllerAutoShow = true,
                controllerShowTimeMilliSeconds = 5_000,
                showFullScreenButton = true
            ),
            volume = 0.0f,
            repeatMode = RepeatMode.NONE,
            modifier = Modifier.fillMaxSize()

        )
    }
}