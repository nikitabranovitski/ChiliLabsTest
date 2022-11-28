package com.branovitski.chililab.ui.gif_list

import androidx.lifecycle.ViewModel
import com.branovitski.chililab.repository.GifDataSourceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ChiliLabViewModel @Inject constructor(
    private val repository: GifDataSourceImpl
) : ViewModel() {

    val queryFlow = MutableStateFlow<String?>(value = null)
    val gifsListItems =
        queryFlow.debounce(300).flatMapLatest { query ->
            repository.getGifs(query)
        }
}
