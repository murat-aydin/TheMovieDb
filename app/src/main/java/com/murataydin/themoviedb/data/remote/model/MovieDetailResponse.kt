package com.murataydin.themoviedb.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailResponse(
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
) : Parcelable



