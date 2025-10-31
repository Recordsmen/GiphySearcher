package chili.labs.giphysearcher.screens.home

data class HomeState(
    val currentQuery: String = "",
    val isNetworkAvailable: Boolean = true
)