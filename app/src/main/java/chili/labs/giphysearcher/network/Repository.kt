package chili.labs.giphysearcher.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import chili.labs.giphysearcher.coroutines.IODispatcher
import chili.labs.giphysearcher.network.domain.GifModel
import chili.labs.giphysearcher.network.mapper.GifMapper
import chili.labs.giphysearcher.pagination.GiphyPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor(
    private val apiService: GiphyApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mapper: GifMapper
) {
    private val pagingConfig = PagingConfig(
        pageSize = GiphyApiService.PAGE_SIZE,
        enablePlaceholders = false
    )

    fun getSearchGifs(query: String): Flow<PagingData<GifModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                GiphyPagingSource(
                    apiService = apiService,
                    query = query,
                    mapper = mapper
                )
            }
        ).flow
    }

    suspend fun getGifById(gifId: String): GifModel = withContext(ioDispatcher) {
        val dto = apiService.getGifById(gifId).gif
        return@withContext mapper.mapDtoToModel(dto)
    }
}