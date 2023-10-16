package com.murataydin.themoviedb.presentation

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashChunkSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.murataydin.themoviedb.databinding.ActivityPlayerBinding
import com.murataydin.themoviedb.presentation.common.Constant
import com.murataydin.themoviedb.presentation.common.Constant.DrmMovie.USER_AGENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var playerView: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializePlayer()
    }

    private fun initializePlayer() {
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(USER_AGENT)
            .setTransferListener(
                DefaultBandwidthMeter.Builder(this)
                    .setResetOnNetworkTypeChange(false)
                    .build()
            )

        val dashChunkSourceFactory: DashChunkSource.Factory = DefaultDashChunkSource.Factory(
            defaultHttpDataSourceFactory
        )
        val manifestDataSourceFactory = DefaultHttpDataSource.Factory().setUserAgent(USER_AGENT)
        val dashMediaSource =
            DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory)
                .createMediaSource(
                    MediaItem.Builder()
                        .setUri(Uri.parse(Constant.DrmMovie.URL))
                        .setDrmConfiguration(
                            MediaItem.DrmConfiguration.Builder(Constant.DrmMovie.DRM_SCHEME)
                                .setLicenseUri(Constant.DrmMovie.DRM_LICENSE_URL).build()
                        )
                        .setMimeType(MimeTypes.APPLICATION_MPD)
                        .setTag(null)
                        .build()
                )

        ExoPlayer.Builder(this)
            .setSeekForwardIncrementMs(10000)
            .setSeekBackIncrementMs(10000)
            .build().also { playerView = it }
        binding.epPlayer.player = playerView
        playerView.setMediaSource(dashMediaSource, true)
        playerView.prepare()
        binding.epPlayer.player?.playWhenReady = true

    }

    private fun closePlayer() {
        binding.epPlayer.player?.let { player ->
            player.pause()
            player.release()
        }
    }

    override fun onPause() {
        super.onPause()
        closePlayer()
    }

    override fun onStop() {
        super.onStop()
        closePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        closePlayer()
    }

}