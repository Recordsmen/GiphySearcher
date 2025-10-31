package chili.labs.giphysearcher.intents

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

/**
 * This is part of the [IntentHandler] and crucial for
 * context receivers to use in addition with [ViewModel]
 * for the purpose of [collectIntents]
 */
interface IntentEvents {
    val intents: Flow<Intent>
}
