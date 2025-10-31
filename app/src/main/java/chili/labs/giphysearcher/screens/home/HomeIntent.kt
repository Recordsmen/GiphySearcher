package chili.labs.giphysearcher.screens.home

import chili.labs.giphysearcher.intents.Intent

sealed class HomeIntent(): Intent {
    data class UpdateQuery(val query: String) : HomeIntent()
    data class NavigateToDetails(val gifId: String) : HomeIntent()
}