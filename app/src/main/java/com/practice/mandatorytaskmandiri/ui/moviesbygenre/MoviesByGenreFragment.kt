package com.practice.mandatorytaskmandiri.ui.moviesbygenre // ktlint-disable filename

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.practice.mandatorytaskmandiri.BuildConfig
import com.practice.mandatorytaskmandiri.R
import com.practice.mandatorytaskmandiri.common.ViewModelFactory
import com.practice.mandatorytaskmandiri.common.adapter.MoviesByGenreAdapter
import com.practice.mandatorytaskmandiri.data.model.GenreModel
import com.practice.mandatorytaskmandiri.data.repositories.MoviesByGenreRepoImplementation
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiConfig
import com.practice.mandatorytaskmandiri.data.source.remote.response.toMovieByGenre
import com.practice.mandatorytaskmandiri.databinding.FragmentMoviesByGenreBinding
import com.practice.mandatorytaskmandiri.utils.ResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "SecondFragment"
class SecondFragment : Fragment() {

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGenreModelBundle() {
        genreModelBundle =
            arguments?.getParcelable(resources.getString(R.string.genre_model_key)) ?: GenreModel(0, "")
        (activity as AppCompatActivity).supportActionBar?.title = genreModelBundle.name + " Movies"
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
                        binding.progressBar6.visibility = View.INVISIBLE
                        val moviesByGenre = result.data.toMovieByGenre()
                        Log.e(TAG, "movieByGenre: $moviesByGenre ")
                        moviesByGenreAdapter.submitList(moviesByGenre.results)
                    }
                    is ResponseUtil.Loading -> {
                        binding.progressBar6.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "test loading", Toast.LENGTH_SHORT).show()
                    }
                    is ResponseUtil.Error -> {
                        binding.progressBar6.visibility = View.INVISIBLE
                        Log.e(TAG, "getGenres: ${result.errorMessage}")
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
        moviesByGenreAdapter = MoviesByGenreAdapter(findNavController())
        val rv = binding.rvMoviesByGenre
        rv.setHasFixedSize(true)
        rv.layoutManager = GridLayoutManager(requireContext(), 3)
        rv.adapter = moviesByGenreAdapter
    }
}
