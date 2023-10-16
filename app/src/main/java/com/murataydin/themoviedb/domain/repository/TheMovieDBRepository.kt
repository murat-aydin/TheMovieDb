package com.murataydin.themoviedb.domain.repository

import com.murataydin.themoviedb.data.remote.model.MovieDetailResponse
import com.murataydin.themoviedb.data.remote.model.MovieImageResponse
import com.murataydin.themoviedb.data.remote.model.MovieResponse
import com.murataydin.themoviedb.data.remote.model.Resource
import kotlinx.coroutines.flow.Flow

interface TheMovieDBRepository {
    fun getDiscoverMovies(page: Int, sortBy: String): Flow<Resource<MovieResponse>>
    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailResponse>>
    fun getMovieImageDetail(movieId: Int): Flow<Resource<MovieImageResponse>>
}