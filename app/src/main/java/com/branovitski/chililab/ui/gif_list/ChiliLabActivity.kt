package com.branovitski.chililab.ui.gif_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.branovitski.chililab.R
import com.branovitski.chililab.databinding.ChiliLabActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.paging.LoadState

@AndroidEntryPoint
class ChiliLabActivity : AppCompatActivity(R.layout.chili_lab_activity) {

    private val binding by viewBinding(ChiliLabActivityBinding::bind)

    private val viewModel by viewModels<ChiliLabViewModel>()

    private var gifsAdapter: GifsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupGifsList()

        gifsAdapter?.addLoadStateListener { state ->
            val refreshState = state.refresh
            binding.gifsList.isVisible = refreshState != LoadState.Loading
            binding.refreshBar.isVisible = refreshState == LoadState.Loading
            if (refreshState is LoadState.Error) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    viewModel.queryFlow.value = null
                } else {
                    viewModel.queryFlow.value = newText
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupGifsList() {
        gifsAdapter = GifsAdapter()
        binding.gifsList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.gifsList.adapter = gifsAdapter

        lifecycleScope.launch {
            viewModel.gifsListItems.collectLatest { gifsAdapter?.submitData(it) }

        }

    }
}