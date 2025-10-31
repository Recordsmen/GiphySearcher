package chili.labs.giphysearcher.intents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

context(model: ViewModel, events: IntentEvents)
inline fun <I : Intent> ViewModel.collectIntents(
    crossinline action: (intent: I) -> Unit,
) {
    this.viewModelScope.launch {
        events.intents.collectLatest {
            @Suppress("UNCHECKED_CAST")
            action(it as I)
        }
    }
}

context(events: IntentEvents)
inline fun <I : Intent> collectIntents(
    scope: CoroutineScope,
    crossinline action: (intent: I) -> Unit,
) {
    scope.launch {
        events.intents.collect {
            @Suppress("UNCHECKED_CAST")
            action(it as I)
        }
    }
}

fun <T> T.onIntent(intent: Intent) where T : ViewModel, T : IntentHandlerAware {
    sendIntent(intent)
}

fun <T> T.onSuspendIntent(intentProvider: suspend () -> Intent) where T : ViewModel, T : IntentHandlerAware {
    sendSuspendIntent(intentProvider)
}

context(model: ViewModel, aware: IntentHandlerAware)
private fun sendIntent(intent: Intent) {
    model.viewModelScope.launch { aware.intentHandler.sendIntent(intent) }
}

context(model: ViewModel, aware: IntentHandlerAware)
private fun sendSuspendIntent(intentProvider: suspend () -> Intent) {
    model.viewModelScope.launch { aware.intentHandler.sendIntent(intentProvider()) }
}
