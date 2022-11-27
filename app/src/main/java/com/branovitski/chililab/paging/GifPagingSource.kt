package com.branovitski.chililab.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.branovitski.chililab.model.GifListItem
import com.branovitski.chililab.network.GIFApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class GifPagingSource @AssistedInject constructor(
    private val service: GIFApiService,
    @Assisted("q") private val query: String? = null
) : PagingSource<Int, GifListItem>() {

    override fun getRefreshKey(state: PagingState<Int, GifListItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)
        return page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifListItem> {
        val page: Int = params.key ?: 0
        val pageSize = params.loadSize

        val response =
            if (query != null) service.getSearchedGifs(query, page, pageSize) else service.getGifs(
                page,
                pageSize
            )

        return if (response.isSuccessful) {
            val gifs =
                response.body()?.data?.map {
                    GifListItem(
                        it.id,
                        it.title,
                        it.url
                    )
                }
            val nextKey = if (gifs?.size!! < pageSize) null else page + 1
            val prevKey = if (page == 0) null else page - 1
            LoadResult.Page(gifs, nextKey, prevKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("q") query: String? = null): GifPagingSource
    }

}