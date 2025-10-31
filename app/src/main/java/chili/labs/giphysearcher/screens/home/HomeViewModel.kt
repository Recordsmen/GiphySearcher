package chili.labs.giphysearcher.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import chili.labs.giphysearcher.intents.IntentHandler
import chili.labs.giphysearcher.intents.IntentHandlerAware
import chili.labs.giphysearcher.intents.collectIntents
import chili.labs.giphysearcher.network.domain.GifModel
import chili.labs.giphysearcher.network.GiphyRepository
import chili.labs.giphysearcher.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GiphyRepository,
    override val intentHandler: IntentHandler,
    private val homeNavigator: HomeNavigator,
    private val networkMonitor: NetworkMonitor
)  : ViewModel(), IntentHandlerAware, IntentHandler by intentHandler {
    private val _uiQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    private val searchTriggerQuery: StateFlow<String> = _uiQuery
        .debounce(500L)
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _uiQuery.value
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchGifs: Flow<PagingData<GifModel>> = searchTriggerQuery
        .flatMapLatest { query ->
            repository.getSearchGifs(query)
        }
        .cachedIn(viewModelScope)

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState(currentQuery = _uiQuery.value)
    )
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        processIntents()
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        networkMonitor.isOnline
            .onEach { isOnline ->
                _state.update { it.copy(isNetworkAvailable = isOnline) }
            }
            .launchIn(viewModelScope)
    }

    private fun processIntents() {
        collectIntents<HomeIntent> { intent ->
            when (intent) {
                is HomeIntent.UpdateQuery -> handleUpdateQuery(intent.query)
                is HomeIntent.NavigateToDetails -> homeNavigator.navigateToDetails(intent.gifId)
            }
        }
    }

    private fun handleUpdateQuery(newQuery: String) {
        _state.update { it.copy(currentQuery = newQuery) }
        _uiQuery.value = newQuery
    }
}