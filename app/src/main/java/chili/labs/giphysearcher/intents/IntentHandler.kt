package chili.labs.giphysearcher.intents

import androidx.compose.runtime.Stable

@Stable
interface IntentHandler : IntentEvents {
    suspend fun sendIntent(intent: Intent)
}
