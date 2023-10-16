package com.murataydin.themoviedb.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieImageResponse(
    val backdrops: List<Backdrop>,
    val posters: List<Poster>
) : Parcelable

@Parcelize
data class Backdrop(
    val file_path: String,
) : Parcelable


@Parcelize
data class Poster(
    val file_path: String,
) : Parcelable