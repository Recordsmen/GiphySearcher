package chili.labs.giphysearcher.screens.details

import chili.labs.giphysearcher.intents.Intent

sealed class DetailsIntent() : Intent {
    object BackClicked : DetailsIntent()
    object RetryClicked : DetailsIntent()
}