package chili.labs.giphysearcher.navigator

import androidx.navigation.NavOptionsBuilder

sealed class NavigatorEvent {
    data object NavigateUp : NavigatorEvent()

    class Navigate(
        val route: String,
        val builder: NavOptionsBuilder.() -> Unit = {
            launchSingleTop = true
        },
    ) : NavigatorEvent() {
        override fun toString(): String = "route=$route"
    }

    data object PopCurrentBackStack : NavigatorEvent()

    data class PopBackStack(
        val route: String,
        val inclusive: Boolean,
        val saveState: Boolean = false,
    ) : NavigatorEvent()
}
