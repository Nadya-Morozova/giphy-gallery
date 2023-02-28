package com.example.giphyapp.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.giphyapp.data.Type
import com.example.giphyapp.databinding.FragmentDetailBinding
import com.example.giphyapp.screens.main.MainViewModel
import com.example.giphyapp.views.adapters.GipHyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var gipHyAdapter: GipHyAdapter

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MainViewModel>()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        gipHyAdapter = GipHyAdapter(item = Type.ONE_ITEM)

        binding.vpGifs.adapter = gipHyAdapter
        binding.vpGifs.post {
            binding.vpGifs.setCurrentItem(args.position, false)
        }

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.listOfGifsFlow.collectLatest {
                gipHyAdapter.submitData(it)
            }
        }
    }

}