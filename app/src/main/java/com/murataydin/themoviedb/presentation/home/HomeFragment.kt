package com.murataydin.themoviedb.presentation.home

import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murataydin.themoviedb.common.extensions.isTablet
import com.murataydin.themoviedb.common.extensions.visibleIf
import com.murataydin.themoviedb.databinding.FragmentHomeBinding
import com.murataydin.themoviedb.domain.AllMoviesUseCase
import com.murataydin.themoviedb.presentation.base.BaseFragment
import com.murataydin.themoviedb.presentation.common.Constant
import com.murataydin.themoviedb.presentation.common.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    MovieAdapter.OnMovieListener {

    private val viewModel by viewModels<HomeViewModel>()
    override val saveBinding: Boolean = true
    private var firstMovieInitialize = false

    override fun bindScreen() {
        getEffect()
        getState()
    }

    private fun getEffect() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is HomeEffect.GoToMovieDetail -> {
                            if (requireActivity().isTablet()) {
                                setFragmentResult(it.movieId)
                            } else {
                                findNavController().navigate(HomeFragmentDirections.homeToDetail(it.movieId))
                            }
                        }

                        is HomeEffect.ShowError -> {
                            handleError(it.throwable)
                        }


                    }
                }
            }
        }
    }

    private fun getState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    setLoadingState(it.isLoading)
                    when {
                        !it.isLoading -> {
                            if (it.allMovies.isNotEmpty()) {
                                it.allMovies.forEach { (key, value) ->
                                    when (key) {
                                        AllMoviesUseCase.MovieType.POPULARITY_DESC -> {
                                            if (!firstMovieInitialize) {
                                                firstMovieInitialize = true
                                                initialDetailFragment(value?.results?.first()?.id ?: 0)

                                            }
                                            setRecyclerview(
                                                binding.tvPopular,
                                                binding.rvPopular,
                                                MovieAdapterModel(getString(key.movieType), value)
                                            )
                                        }

                                        AllMoviesUseCase.MovieType.REVENUE_DESC -> {
                                            setRecyclerview(
                                                binding.tvRevenue,
                                                binding.rvRevenueMovies,
                                                MovieAdapterModel(getString(key.movieType), value)
                                            )
                                        }

                                        AllMoviesUseCase.MovieType.PRIMARY_RELEASE_DATE_DESC -> {
                                            setRecyclerview(
                                                binding.tvPrimary,
                                                binding.rvPrimary,
                                                MovieAdapterModel(getString(key.movieType), value)
                                            )
                                        }

                                        AllMoviesUseCase.MovieType.VOTE_AVERAGE_DESC -> {
                                            setRecyclerview(
                                                binding.tvVote,
                                                binding.rvVote,
                                                MovieAdapterModel(getString(key.movieType), value)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setRecyclerview(
        textView: TextView,
        recyclerView: RecyclerView,
        movieAdapterModel: MovieAdapterModel
    ) {
        textView.text = movieAdapterModel.title
        recyclerView.setHasFixedSize(true)
        if (recyclerView.adapter == null) {
            val adapter = MovieAdapter(this).apply {
                submitList(movieAdapterModel.results?.results)
            }
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object :
                PaginationScrollListener(recyclerView.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.setEvent(HomeEvent.LoadMore(movieAdapterModel.title))
                }

                override fun isLastPage(): Boolean =
                    (recyclerView.adapter as MovieAdapter).itemCount >= (movieAdapterModel.results?.total_pages
                        ?: 0)

                override fun isLastedPage() = Unit
                override fun isNotLastedPage() = Unit
            })
        } else {
            movieAdapterModel.results?.results?.let {
                (recyclerView.adapter as MovieAdapter).updateList(
                    it
                )
            }
        }
    }

    private fun setFragmentResult(movieId: Int) {
        requireActivity().supportFragmentManager.setFragmentResult(
            Constant.DetailDataListener.DETAIL_SCREEN,
            bundleOf(
                Constant.DetailDataListener.DETAIL_MOVIE_ID to movieId
            )
        )
    }

    private fun initialDetailFragment(movieId: Int) {
        binding.detailContainer.visibleIf(requireActivity().isTablet())
        setFragmentResult(movieId)
    }

    override fun onClickMovie(movieId: Int) {
        viewModel.setEvent(HomeEvent.OnClickMovieDetail(movieId))
    }
}