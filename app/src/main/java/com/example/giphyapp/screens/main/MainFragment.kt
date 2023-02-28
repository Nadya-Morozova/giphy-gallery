package com.example.giphyapp.screens.main

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphyapp.R
import com.example.giphyapp.data.Type
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.databinding.FragmentMainBinding
import com.example.giphyapp.network.paging.ListLoadStateAdapter
import com.example.giphyapp.utils.BlockListGifs
import com.example.giphyapp.utils.ConnectivityTracker
import com.example.giphyapp.utils.OnGifClick
import com.example.giphyapp.views.adapters.GipHyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), BlockListGifs, OnGifClick {

    @Inject
    lateinit var connectivityTracker: ConnectivityTracker

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var gipHyAdapter: GipHyAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        loadGifs(null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search)
        val search = searchItem.actionView as SearchView

        val closeButton = search.findViewById<ImageView?>(
            search.context.resources.getIdentifier(
                "android:id/search_close_btn", null, null
            )
        )
        closeButton.setOnClickListener {
            loadGifs(null)
            search.onActionViewCollapsed()
            search.clearFocus()
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadGifs(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun loadGifs(searchQuery: String?) {
        createNewAdapter()
        viewModel.setSearchQuery(searchQuery)
        subscribeAdapter()
    }

    private fun createNewAdapter() {
        gipHyAdapter = GipHyAdapter(this, this, Type.LIST)
        concatAdapter = gipHyAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvGifs.adapter = concatAdapter
    }

    private fun subscribeAdapter() {
        lifecycleScope.launch {
            viewModel.listOfGifsFlow.collectLatest {
                gipHyAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvGifs.layoutManager = gridLayoutManager
        binding.rvGifs.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == gipHyAdapter.itemCount && gipHyAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
    }

    override fun addToBlockList(id: String) {
        viewModel.insertGifsToBlockList(BlockedGipHy(id))
    }

    override fun onGifClick(position: Int) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(position)
        )
    }

}