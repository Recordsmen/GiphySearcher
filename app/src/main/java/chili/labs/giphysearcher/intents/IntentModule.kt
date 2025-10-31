package chili.labs.giphysearcher.intents

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class IntentModule {

    /**
     * This intent handler uses a channel and hence the events must be handled
     * @param handler IntentHandlerChannel
     * @return IntentHandler
     */
    @Binds
    abstract fun intentHandler(handler: IntentHandlerChannel): IntentHandler
}
