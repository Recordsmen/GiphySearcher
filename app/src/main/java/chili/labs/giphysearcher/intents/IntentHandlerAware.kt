package chili.labs.giphysearcher.intents

import androidx.lifecycle.ViewModel

/**
 * A contract that's needed for a [ViewModel]
 * to use an [IntentHandler] for the purpose of handling the events
 * needed for [collectIntents]
 */
interface IntentHandlerAware {
    val intentHandler: IntentHandler
}
