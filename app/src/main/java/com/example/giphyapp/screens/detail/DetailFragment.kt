package com.example.giphyapp.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.giphyapp.data.Type
import com.example.giphyapp.databinding.FragmentDetailBinding
import com.example.giphyapp.screens.main.MainViewModel
import com.example.giphyapp.utils.ConnectivityTracker
import com.example.giphyapp.views.adapters.GipHyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        private const val POSITION = "position"
        fun newInstance(position: Int): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putInt(POSITION, position)
            }
            return fragment
        }
    }

    @Inject
    lateinit var connectivityTracker: ConnectivityTracker

    private var position: Int? = null
    private lateinit var binding: FragmentDetailBinding
    private lateinit var gipHyAdapter: GipHyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt(POSITION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        if (!connectivityTracker.isNetworkConnected(requireContext())) {
            Toast.makeText(requireContext(), "Ooops. Something went wrong :(", Toast.LENGTH_SHORT)
                .show()
        }

        gipHyAdapter = GipHyAdapter(item = Type.ONE_ITEM)

        binding.vpGifs.adapter = gipHyAdapter
        lifecycleScope.launch {
            delay(100L)
            binding.vpGifs.setCurrentItem(position ?: 0, false)
        }

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            MainViewModel.commonFlow?.collectLatest {
                gipHyAdapter.submitData(it)
            }
        }
    }

}