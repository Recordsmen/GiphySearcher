package chili.labs.giphysearcher.network.mapper

import chili.labs.giphysearcher.model.GifDto
import chili.labs.giphysearcher.network.domain.GifModel
import chili.labs.giphysearcher.network.domain.ImageUrls
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifMapper @Inject constructor() {

    fun mapDtoToModel(dto: GifDto): GifModel {
        val title = dto.title

        val fixedUrl = dto.images.fixedHeight.webpUrl ?: dto.images.fixedHeight.url
        val originalUrl = dto.images.original.webpUrl ?: dto.images.original.url

        return GifModel(
            id = dto.id,
            title = title,
            urls = ImageUrls(
                fixedHeightUrl = fixedUrl,
                originalUrl = originalUrl
            )
        )
    }
}