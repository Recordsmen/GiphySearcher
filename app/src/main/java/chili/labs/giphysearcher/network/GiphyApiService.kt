package chili.labs.giphysearcher.network

import chili.labs.giphysearcher.BuildConfig
import chili.labs.giphysearcher.model.GiphyResponse
import chili.labs.giphysearcher.model.SingleGifResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyApiService {
    companion object {
        const val API_KEY = BuildConfig.GIPHY_API_KEY
        const val BASE_URL = BuildConfig.GIPHY_BASE_URL
        const val PAGE_SIZE = 10
    }

    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String,
        @Query("limit") limit: Int = PAGE_SIZE,
        @Query("offset") offset: Int,
    ): GiphyResponse

    @GET("gifs/{gifId}")
    suspend fun getGifById(
        @Path("gifId") gifId: String,
        @Query("api_key") apiKey: String = API_KEY
    ): SingleGifResponse
}