package com.practice.mandatorytaskmandiri.ui.genres

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.mandatorytaskmandiri.common.ViewModelFactory
import com.practice.mandatorytaskmandiri.data.repositories.GenreRepoImplementation
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiConfig
import com.practice.mandatorytaskmandiri.data.source.remote.response.toListGenre
import com.practice.mandatorytaskmandiri.databinding.FragmentGenreListBinding
import com.practice.mandatorytaskmandiri.utils.ResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreListFragment : Fragment() {
    private val TAG = "GenreListFragment"
    private var _binding: FragmentGenreListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val vm: GenreListFragmentViewModel by viewModels {
        ViewModelFactory(GenreRepoImplementation.getInstance(ApiConfig.provideApiService(requireContext())))
    }
    private val genreListAdapter = GenreListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        getGenres()
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGenres() {
        CoroutineScope(Dispatchers.Main).launch {
            vm.getGenre().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResponseUtil.Success -> {
                        // show rv
                        binding.progressBar5.visibility = View.INVISIBLE
                        val listGenre = result.data.toListGenre()
                        Log.e(TAG, "getGenres: $listGenre ")
                        genreListAdapter.submitList(listGenre)
                    }
                    is ResponseUtil.Loading -> {
                        binding.progressBar5.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "test loading", Toast.LENGTH_SHORT).show()
                    }
                    is ResponseUtil.Error -> {
                        binding.progressBar5.visibility = View.INVISIBLE
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
        val rv = binding.rvMoviesGenre
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = genreListAdapter
    }
}
