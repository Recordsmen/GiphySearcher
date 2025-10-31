package chili.labs.giphysearcher.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chili.labs.giphysearcher.intents.IntentHandler
import chili.labs.giphysearcher.intents.IntentHandlerAware
import chili.labs.giphysearcher.intents.collectIntents
import chili.labs.giphysearcher.network.GiphyRepository
import chili.labs.giphysearcher.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: GiphyRepository,
    private val detailsArgs: DetailsArgs,
    private val detailsNavigator: DetailsNavigator,
    override val intentHandler: IntentHandler,
    private val networkMonitor: NetworkMonitor
) : ViewModel(), IntentHandlerAware, IntentHandler by intentHandler {

    private val _screenState = MutableStateFlow(DetailsState(isLoading = false))
    val screenState: StateFlow<DetailsState> = _screenState.asStateFlow()

    init {
        fetchGifDetails()
        observeNetworkStatus()
        processIntents()
    }

    private fun processIntents(){
        collectIntents<DetailsIntent> { intent ->
            when (intent) {
                DetailsIntent.BackClicked -> { detailsNavigator.navigateBack() }
                DetailsIntent.RetryClicked -> { fetchGifDetails() }
            }
        }
    }

    private fun observeNetworkStatus() {
        networkMonitor.isOnline
            .onEach { isOnline ->
                _screenState.update { it.copy(isNetworkAvailable = isOnline) }
                if (isOnline && _screenState.value.gif == null && !_screenState.value.isLoading) {
                    fetchGifDetails()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchGifDetails() {
        val gifId = detailsArgs.gifId
        if (_screenState.value.isLoading && _screenState.value.gif?.id == gifId) return
        viewModelScope.launch {
            _screenState.update {
                it.copy(isLoading = true, error = null, gif = null) 
            }
            try {
                val resultGif = repository.getGifById(gifId)
                _screenState.update {
                    it.copy(
                        isLoading = false,
                        gif = resultGif
                    ) 
                }
            } catch (e: Exception) {
                _screenState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    ) 
                }
            }
        }
    }
}