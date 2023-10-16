package com.murataydin.themoviedb.presentation.common

import com.google.android.exoplayer2.C

object Constant {

    object DetailDataListener {
        const val DETAIL_SCREEN = "detailScreen"
        const val DETAIL_MOVIE_ID = "detailMovieId"
    }

    object DrmMovie {
        const val URL = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
        const val DRM_LICENSE_URL =
            "https://proxy.uat.widevine.com/proxy?video_id=2015_tears&provider=widevine_test"
        val DRM_SCHEME = C.WIDEVINE_UUID
        const val USER_AGENT = "ExoPlayer-Drm"
    }
    const val MOVIE_ID = "movieId"

}