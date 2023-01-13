package com.practice.mandatorytaskmandiri.ui.detailmovie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.mandatorytaskmandiri.BuildConfig
import com.practice.mandatorytaskmandiri.R
import com.practice.mandatorytaskmandiri.common.ViewModelFactory
import com.practice.mandatorytaskmandiri.common.adapter.ReviewMovieAdapter
import com.practice.mandatorytaskmandiri.common.helper.CollapsingToolbarHelper
import com.practice.mandatorytaskmandiri.data.model.MovieByGenreModel
import com.practice.mandatorytaskmandiri.data.model.MovieReviewModel
import com.practice.mandatorytaskmandiri.data.repositories.DetailMovieRepoImplementation
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiConfig
import com.practice.mandatorytaskmandiri.data.source.remote.response.toDetailMovieModel
import com.practice.mandatorytaskmandiri.data.source.remote.response.toMovieTrailerModel
import com.practice.mandatorytaskmandiri.databinding.FragmentDetailMoviesBinding
import com.practice.mandatorytaskmandiri.utils.ResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailMoviesFragment : Fragment() {
    private val TAG = "DetailMoviesFragment"
    private var _binding: FragmentDetailMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: DetailMoviesViewModel by viewModels {
        ViewModelFactory().ViewModelFactoryDetailMovies(
            DetailMovieRepoImplementation.getInstance(ApiConfig.provideApiService(requireContext()))
        )
    }
    private var moviesByGenreBundle: MovieByGenreModel.Result? = null
    private lateinit var layoutManager: LinearLayoutManager

    // paging
    private var isLastPage = false
    private var isLoadMore = false
    private var _page: MutableStateFlow<Int> = MutableStateFlow(1)
    private val page = _page.value
    private lateinit var reviewMovieAdapter: ReviewMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initRv()
        getBundle()
        getMovieDetail()
        moviesByGenreBundle?.id?.let { getUserReview(page = page, it) }
        showTrailer()
    }

    private fun showTrailer() {
        binding.btnShowTrailer.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                moviesByGenreBundle?.id?.let { movieId ->
                    vm.getMovieTrailer(
                        apikey = BuildConfig.API_KEY_TOKEN,
                        movieId
                    ).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is ResponseUtil.Success -> {
                                val resultTrailer = result.data
                                val movieTrailerModel = resultTrailer.toMovieTrailerModel()
                                val filteredMovieTrailerModel = movieTrailerModel.results?.filter {
                                    it?.name == getString(R.string.official_trailer)
                                }
                                filteredMovieTrailerModel?.map {
                                    val url = "${BuildConfig.URL_YOUTUBE}${it?.key}"
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse(url)
                                    startActivity(intent)
                                }
                            }
                            is ResponseUtil.Loading -> {
                                showProgressBar(true)
                            }
                            is ResponseUtil.Error -> {
                                showProgressBar(false)
                                Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else -> {
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRv() {
        layoutManager = LinearLayoutManager(requireContext())
        reviewMovieAdapter = ReviewMovieAdapter(findNavController())
        val rv = binding.rvUserReview
        rv.setHasFixedSize(true)
        rv.layoutManager = layoutManager
        rv.adapter = reviewMovieAdapter
    }

    private fun getUserReview(page: Int, movieId: Int) {
        initRv()
        CoroutineScope(Dispatchers.Main).launch {
            vm.getReviewMovie(
                apikey = BuildConfig.API_KEY_TOKEN,
                language = getString(R.string.language),
                page = page,
                movieId
            ).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResponseUtil.Success -> {
                        val resultReviews = result.data
                        val listResult = mutableListOf<MovieReviewModel.Result>()
                        resultReviews.results?.map {
                            val authorDetails = MovieReviewModel.Result.AuthorDetails(
                                avatarPath = it?.authorDetails?.avatarPath,
                                name = it?.authorDetails?.name,
                                rating = it?.authorDetails?.rating,
                                username = it?.authorDetails?.username
                            )
                            val dataResult = MovieReviewModel.Result(
                                author = it?.author,
                                authorDetails = authorDetails,
                                content = it?.content,
                                createdAt = it?.createdAt,
                                id = it?.id,
                                updatedAt = it?.updatedAt,
                                url = it?.url
                            )
                            listResult.add(dataResult)
                        }
                        when {
                            listResult.size < 1 -> {
                                binding.rvUserReview.visibility = View.INVISIBLE
                                binding.progressBarReview.visibility = View.INVISIBLE
                                binding.tvHasNoReview.visibility = View.VISIBLE
                            }
                            else -> {
                                binding.tvHasNoReview.visibility = View.INVISIBLE
                                binding.progressBarReview.visibility = View.INVISIBLE
                                reviewMovieAdapter.submitList(listResult)
                            }
                        }
                    }
                    is ResponseUtil.Loading -> {
                        showProgressBar(true)
                    }
                    is ResponseUtil.Error -> {
                        showProgressBar(false)
                        Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun getMovieDetail() {
        CoroutineScope(Dispatchers.Main).launch {
            moviesByGenreBundle?.id?.let {
                vm.getDetailMovie(
                    apikey = BuildConfig.API_KEY_TOKEN,
                    language = getString(R.string.language),
                    movieId = it
                ).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResponseUtil.Success -> {
                            // show rv
                            binding.apply {
                                showProgressBar(false)
                                val detailMovie = result.data.toDetailMovieModel()
                                tvStatus.text = detailMovie.status
                                val genreList = mutableListOf<String>()
                                detailMovie.genres?.map { genre ->
                                    genre?.name?.let { it1 -> genreList.add(it1) }
                                }
                                tvGenres.text = genreList.joinToString(separator = ", ")
                                tvReleaseDate.text = detailMovie.releaseDate
                                tvOverview.text = detailMovie.overview
                            }
                        }
                        is ResponseUtil.Loading -> {
                            showProgressBar(true)
                        }
                        is ResponseUtil.Error -> {
                            showProgressBar(false)
                            //                        Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT)
                            //                            .show()
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun getBundle() {
        moviesByGenreBundle = arguments?.getParcelable(getString(R.string.movie_by_genre_model_key))
        CollapsingToolbarHelper().apply {
            setImageToolbar(
                activity?.findViewById(R.id.imgCollapsingToolbar),
                moviesByGenreBundle?.backdropPath,
                0
            )
            moviesByGenreBundle?.originalTitle?.let {
                setTittleToolbar(
                    activity?.findViewById(R.id.collapsingToolbarLayout),
                    it
                )
            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.apply {
            when {
                show -> {
                    progressBarStatus.visibility = View.INVISIBLE
                    progressBarReleaseDate.visibility = View.INVISIBLE
                    progressBarGenres.visibility = View.INVISIBLE
                    progressBarContent.visibility = View.INVISIBLE
                }
                else -> {
                    progressBarStatus.visibility = View.INVISIBLE
                    progressBarReleaseDate.visibility = View.INVISIBLE
                    progressBarGenres.visibility = View.INVISIBLE
                    progressBarContent.visibility = View.INVISIBLE
                }
            }
        }
    }
}
