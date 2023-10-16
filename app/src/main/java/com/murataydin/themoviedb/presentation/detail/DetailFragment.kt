package com.murataydin.themoviedb.presentation.detail

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.murataydin.themoviedb.BuildConfig
import com.murataydin.themoviedb.R
import com.murataydin.themoviedb.data.remote.model.MovieDetailResponse
import com.murataydin.themoviedb.data.remote.model.MovieImageResponse
import com.murataydin.themoviedb.databinding.FragmentDetailBinding
import com.murataydin.themoviedb.presentation.VideoPlayerActivity
import com.murataydin.themoviedb.presentation.base.BaseFragment
import com.murataydin.themoviedb.presentation.common.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()

    override fun bindScreen() {
        getEffect()
        getState()
        setUI()
        setFragmentResultListener()
    }

    private fun getEffect() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.effect.collect {
                    when (it) {
                        is DetailEffect.ShowError -> {
                            handleError(it.throwable)
                        }
                    }
                }
            }
        }
    }

    private fun getState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    setLoadingState(it.isLoading)
                    if (!it.isLoading) {
                        it.movieImageDetail?.let { movieImage ->
                            setImages(movieImage)
                        }
                        it.movieDetail?.let { movieDetail ->
                            setDesc(movieDetail)
                        }
                    }
                }
            }
        }
    }

    private fun setUI() {
        with(binding) {
            ivPlayBtn.setOnClickListener {
                val intent = Intent(requireContext(), VideoPlayerActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun setImages(movieImage: MovieImageResponse) {
        with(binding) {
            if (movieImage.backdrops.isNotEmpty()) {
                ivMovieBackdrop.load("${BuildConfig.IMAGE_BASE_URL}${movieImage.backdrops.first().file_path}") {
                    error(R.drawable.ic_image)
                    placeholder(R.drawable.ic_image)
                    fallback(R.drawable.ic_image)
                }
            }
            if (movieImage.posters.isNotEmpty()) {
                ivMoviePoster.load("${BuildConfig.IMAGE_BASE_URL}${movieImage.posters.first().file_path}") {
                    error(R.drawable.ic_image)
                    placeholder(R.drawable.ic_image)
                    fallback(R.drawable.ic_image)
                }
            }
        }
    }

    private fun setDesc(movieDetail: MovieDetailResponse) {
        with(binding) {
            tvMovieTitle.text = movieDetail.title
            tvMovieStarCount.text = movieDetail.vote_average.toString()
            tvMovieDate.text = movieDetail.release_date
            tvMovieDesc.text = movieDetail.overview
        }
    }


    private fun setFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            Constant.DetailDataListener.DETAIL_SCREEN,
            this,
        ) { _, bundle ->
            viewModel.getMovieDetail(bundle.getInt(Constant.DetailDataListener.DETAIL_MOVIE_ID))
        }
    }

}