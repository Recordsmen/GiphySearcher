package chili.labs.giphysearcher.navigator

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AppNavigatorImpl @Inject constructor() : AppNavigator {

    private val _navigatorEvents = Channel<NavigatorEvent>(Channel.BUFFERED)
    override val navigatorEvents = _navigatorEvents.receiveAsFlow()

    override fun navigateUp() {
        _navigatorEvents.trySend(NavigatorEvent.NavigateUp)
    }

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        _navigatorEvents.trySend(NavigatorEvent.Navigate(route, builder))
    }

    override fun popCurrentBackStack() {
        _navigatorEvents.trySend(NavigatorEvent.PopCurrentBackStack)
    }

    override fun popBackStack(destination: String, inclusive: Boolean, saveState: Boolean) {
        _navigatorEvents.trySend(NavigatorEvent.PopBackStack(destination, inclusive, saveState))
    }
}
