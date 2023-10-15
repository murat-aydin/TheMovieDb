package com.murataydin.themoviedb.data.remote

import com.murataydin.themoviedb.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBService {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") pageNumber: Int,
        @Query("sort_by") sortBy: String
    ): Response<MovieResponse>
}