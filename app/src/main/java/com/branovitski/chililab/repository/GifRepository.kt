package com.branovitski.chililab.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.branovitski.chililab.network.GIFApiService
import com.branovitski.chililab.ui.gif_list.GifListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GifDataSourceImpl @Inject constructor(
    private val service: GIFApiService
) {

    fun getGifs(query: String? = null): Flow<PagingData<GifListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GifPagingSource(service, query)
            }
        ).flow
    }
}