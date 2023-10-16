package com.murataydin.themoviedb.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.murataydin.themoviedb.data.remote.model.MovieDetailResponse
import com.murataydin.themoviedb.data.remote.model.MovieImageResponse
import com.murataydin.themoviedb.domain.MovieDetailUseCase
import com.murataydin.themoviedb.presentation.base.BaseViewModel
import com.murataydin.themoviedb.presentation.base.IEffect
import com.murataydin.themoviedb.presentation.base.IEvent
import com.murataydin.themoviedb.presentation.base.IState
import com.murataydin.themoviedb.presentation.common.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private var movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel<DetailEvent, DetailState, DetailEffect>() {

    init {
        savedStateHandle.get<Int>(Constant.MOVIE_ID)?.let {
            getMovieDetail(it)
        }
    }

    override fun setInitialState(): DetailState = DetailState(isLoading = false)

    override fun handleEvents(event: DetailEvent) {
        when (event) {
            else -> {}
        }
    }

    fun getMovieDetail(movieId: Int) {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            movieDetailUseCase.invoke(movieId).collect {
                when (it) {
                    is MovieDetailUseCase.MovieDetailState.Success -> {
                        setState {
                            copy(
                                isLoading = false,
                                movieDetail = it.movieDetailResponse[MovieDetailUseCase.MovieDetailType.DETAIL] as MovieDetailResponse,
                                movieImageDetail = it.movieDetailResponse[MovieDetailUseCase.MovieDetailType.IMAGE_DETAIL] as MovieImageResponse
                            )
                        }
                    }

                    is MovieDetailUseCase.MovieDetailState.Error -> {
                        setState { copy(isLoading = false) }
                        setEffect { DetailEffect.ShowError(it.throwable) }
                    }
                }
            }
        }
    }
}

data class DetailState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetailResponse? = null,
    val movieImageDetail: MovieImageResponse? = null
) : IState

sealed interface DetailEffect : IEffect {
    data class ShowError(val throwable: Throwable?) : DetailEffect
}

sealed interface DetailEvent : IEvent