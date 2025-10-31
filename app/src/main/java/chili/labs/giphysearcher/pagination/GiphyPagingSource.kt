package chili.labs.giphysearcher.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import chili.labs.giphysearcher.network.mapper.GifMapper
import chili.labs.giphysearcher.network.domain.GifModel
import chili.labs.giphysearcher.network.GiphyApiService
import retrofit2.HttpException
import java.io.IOException

class GiphyPagingSource(
    private val apiService: GiphyApiService,
    private val query: String,
    private val mapper: GifMapper
) : PagingSource<Int, GifModel>() {
    companion object {
        const val STARTING_OFFSET = 0
    }

    override fun getRefreshKey(state: PagingState<Int, GifModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(GiphyApiService.PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(GiphyApiService.PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifModel> {
        val offset = params.key ?: STARTING_OFFSET

        return try {
            val response = apiService.searchGifs(query = query, offset = offset)

            val models = response.gifs.map { mapper.mapDtoToModel(it) }

            val responseGifsIds = response.gifs.map { it.id }

            Log.d("GiphyPagingSource","offset:${offset}, responseGifsIds:${responseGifsIds.toString()}")

            val nextOffset = if (models.isNotEmpty()) {
                offset + GiphyApiService.PAGE_SIZE
            } else {
                null
            }

            LoadResult.Page(
                data = models,
                prevKey = if (offset == STARTING_OFFSET) null else offset - GiphyApiService.PAGE_SIZE,
                nextKey = nextOffset
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}