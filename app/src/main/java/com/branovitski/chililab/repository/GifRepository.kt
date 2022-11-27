package com.branovitski.chililab.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.branovitski.chililab.model.GifListItem
import com.branovitski.chililab.network.GIFApiService
import com.branovitski.chililab.paging.GifPagingSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class GifRepository @Inject constructor(
    private val pagingSourceFactory: GifPagingSource.Factory,
) {

    fun queryAll(query: String? = null): PagingSource<Int, GifListItem> {
        return pagingSourceFactory.create(query)
    }
}