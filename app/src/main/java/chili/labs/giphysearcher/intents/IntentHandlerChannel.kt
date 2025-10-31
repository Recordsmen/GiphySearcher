package chili.labs.giphysearcher.intents

import chili.labs.giphysearcher.coroutines.MainImmediateDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext

class IntentHandlerChannel @Inject constructor(
    @MainImmediateDispatcher private val dispatcher: CoroutineDispatcher,
) : IntentHandler {

    private val _intentsFlow = MutableSharedFlow<Intent>(extraBufferCapacity = Int.MAX_VALUE)

    override val intents = _intentsFlow.asSharedFlow()

    override suspend fun sendIntent(intent: Intent) = withContext(dispatcher) {
        _intentsFlow.emit(intent)
    }
}
