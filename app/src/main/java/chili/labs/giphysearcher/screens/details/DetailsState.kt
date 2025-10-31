package chili.labs.giphysearcher.screens.details

import chili.labs.giphysearcher.network.domain.GifModel

data class DetailsState(
    val gif: GifModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNetworkAvailable: Boolean = true
)