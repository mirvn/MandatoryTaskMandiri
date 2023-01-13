package com.practice.mandatorytaskmandiri.ui.moviesbygenre // ktlint-disable filename

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practice.mandatorytaskmandiri.BuildConfig
import com.practice.mandatorytaskmandiri.R
import com.practice.mandatorytaskmandiri.common.ViewModelFactory
import com.practice.mandatorytaskmandiri.common.adapter.MoviesByGenreAdapter
import com.practice.mandatorytaskmandiri.common.helper.CollapsingToolbarHelper
import com.practice.mandatorytaskmandiri.common.helper.PaginationScrollListener
import com.practice.mandatorytaskmandiri.data.model.GenreModel
import com.practice.mandatorytaskmandiri.data.model.MovieByGenreModel
import com.practice.mandatorytaskmandiri.data.repositories.MoviesByGenreRepoImplementation
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiConfig
import com.practice.mandatorytaskmandiri.data.source.remote.response.toMovieByGenre
import com.practice.mandatorytaskmandiri.databinding.FragmentMoviesByGenreBinding
import com.practice.mandatorytaskmandiri.utils.ResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SecondFragment"

class MoviesByGenreFragment : Fragment() {

    private var _binding: FragmentMoviesByGenreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var genreModelBundle: GenreModel
    private lateinit var moviesByGenreAdapter: MoviesByGenreAdapter
    private val vm: MoviesByGenreFragmentViewModel by viewModels {
        ViewModelFactory().ViewModelFactoryMoviesByGenre(
            MoviesByGenreRepoImplementation.getInstance(ApiConfig.provideApiService(requireContext()))
        )
    }
    private lateinit var layoutManager: GridLayoutManager

    // paging
    private var isLastPage = false
    private var isLoadMore = false
    var page = 1

    private val mutableResult = mutableListOf<MovieByGenreModel.Result>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        setGenreModelBundle()
        getDiscoverMoviesByGenre(
            api_key = BuildConfig.API_KEY_TOKEN,
            language = getString(R.string.language),
            sort_by = getString(R.string.sort_by_for_movieByGenre),
            include_adult = getString(R.string.true_string),
            include_video = getString(R.string.true_string),
            page = "1",
            with_genres = genreModelBundle.id.toString(),
            with_watch_monetization_types = getString(R.string.with_watch_monetization_types_flatrate)
        )
        loadMore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGenreModelBundle() {
        genreModelBundle =
            arguments?.getParcelable(resources.getString(R.string.genre_model_key)) ?: GenreModel(
                0,
                ""
            )
        CollapsingToolbarHelper().apply {
            setImageToolbar(
                activity?.findViewById(R.id.imgCollapsingToolbar),
                null,
                R.drawable.rectange_shape_collapsing_toolbar_bg
            )
            setTittleToolbar(
                activity?.findViewById(R.id.collapsingToolbarLayout),
                genreModelBundle.name + " Movies"
            )
        }
    }

    private fun getDiscoverMoviesByGenre(
        api_key: String,
        language: String,
        sort_by: String,
        include_adult: String,
        include_video: String,
        page: String,
        with_genres: String,
        with_watch_monetization_types: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            vm.getMoviesByGenre(
                api_key = api_key,
                language = language,
                sort_by = sort_by,
                include_adult = include_adult,
                include_video = include_video,
                page = page,
                with_genres = with_genres,
                with_watch_monetization_types = with_watch_monetization_types
            ).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResponseUtil.Success -> {
                        // show rv
                        isLoadMore = false
                        binding.progressBar6.visibility = View.GONE
                        val moviesByGenre = result.data.toMovieByGenre()
                        moviesByGenre.results.map { movieResult ->
                            mutableResult.add(movieResult)
                            moviesByGenreAdapter.submitList(mutableResult)
                            moviesByGenreAdapter.notifyItemRangeChanged(0, mutableResult.size)
                        }
                    }
                    is ResponseUtil.Loading -> {
                        binding.progressBar6.visibility = View.VISIBLE
                    }
                    is ResponseUtil.Error -> {
                        isLastPage = true
                        binding.progressBar6.visibility = View.GONE
                        Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun initRv() {
        layoutManager = GridLayoutManager(requireContext(), 3)
        moviesByGenreAdapter = MoviesByGenreAdapter(findNavController())
        val rv = binding.rvMoviesByGenre
        rv.setHasFixedSize(true)
        rv.layoutManager = layoutManager
        rv.adapter = moviesByGenreAdapter
    }

    private fun loadMore() {
        binding.apply {
//            isLoadMore = true
            rvMoviesByGenre.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
                override fun isLastPage(): Boolean = isLastPage

                override fun isLoading(): Boolean = isLoadMore

                override fun loadMoreItems() {
                    isLoadMore = true
                    page++
                    progressBar6.visibility = View.VISIBLE
                    viewLifecycleOwner.lifecycleScope.launch {
                        getDiscoverMoviesByGenre(
                            api_key = BuildConfig.API_KEY_TOKEN,
                            language = getString(R.string.language),
                            sort_by = getString(R.string.sort_by_for_movieByGenre),
                            include_adult = getString(R.string.true_string),
                            include_video = getString(R.string.true_string),
                            page = page.toString(),
                            with_genres = genreModelBundle.id.toString(),
                            with_watch_monetization_types = getString(R.string.with_watch_monetization_types_flatrate)
                        )
                    }
                }
            })
        }
    }
}
