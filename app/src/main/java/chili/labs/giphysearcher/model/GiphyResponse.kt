package chili.labs.giphysearcher.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GiphyResponse(
    @SerializedName("data") val gifs: List<GifDto>,
    @SerializedName("pagination") val pagination: PaginationDto
)

@Keep
data class SingleGifResponse(
    @SerializedName("data") val gif: GifDto
)

@Keep
data class GifDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: ImagesDto,
)

@Keep
data class ImagesDto(
    @SerializedName("fixed_height") val fixedHeight: FixedHeightDto,
    @SerializedName("original") val original: OriginalDto,
)

@Keep
data class FixedHeightDto(
    @SerializedName("url") val url: String,
    @SerializedName("webp") val webpUrl: String?,
)

@Keep
data class OriginalDto(
    @SerializedName("url") val url: String,
    @SerializedName("webp") val webpUrl: String?,
)

@Keep
data class PaginationDto(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int
)