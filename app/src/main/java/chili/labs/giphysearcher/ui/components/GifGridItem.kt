package chili.labs.giphysearcher.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import chili.labs.giphysearcher.network.domain.GifModel
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@Composable
fun GifGridItem(
    gif: GifModel,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(gif.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(gif.urls.originalUrl)
                .decoderFactory(ImageDecoderDecoder.Factory())
                .build(),
            contentDescription = gif.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
