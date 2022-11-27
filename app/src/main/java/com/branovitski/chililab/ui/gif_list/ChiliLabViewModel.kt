package com.branovitski.chililab.ui.gif_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.branovitski.chililab.paging.GifPagingSource
import com.branovitski.chililab.repository.GifRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ChiliLabViewModel @Inject constructor(
    private val repository: GifRepository
) : ViewModel() {

    val queryFlow = MutableStateFlow<String?>(value = null)
    val gifsListItems =
        queryFlow.debounce(300).flatMapLatest { query ->
            Pager(
                PagingConfig(pageSize = 1)
            ) {
                repository.queryAll(query)
            }.flow
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    PagingData.empty()
                )
        }
}
