package com.example.giphyapp.screens.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.giphyapp.data.Type
import com.example.giphyapp.databinding.FragmentDetailBinding
import com.example.giphyapp.screens.main.MainViewModel
import com.example.giphyapp.views.adapters.GipHyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Arrays

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        const val POSITION_KEY = "POSITION"
    }

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) initObservers()
        gipHyAdapter = GipHyAdapter(item = Type.ONE_ITEM)
        binding.vpGifs.adapter = gipHyAdapter

        binding.vpGifs.post {
            binding.vpGifs.setCurrentItem(
                savedInstanceState?.getInt(POSITION_KEY) ?: args.position,
                false
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.vpGifs.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                outState.putInt(POSITION_KEY, position)
            }
        })
    }

    private fun initObservers() {
        viewModel.giphyPagingData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                gipHyAdapter.submitData(it)
            }
        }
    }

}
