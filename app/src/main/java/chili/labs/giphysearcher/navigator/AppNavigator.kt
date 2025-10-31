package chili.labs.giphysearcher.navigator

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface AppNavigator {

    val navigatorEvents: Flow<NavigatorEvent>

    fun navigateUp()

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true },
    )

    fun popCurrentBackStack()

    fun popBackStack(
        destination: String,
        inclusive: Boolean,
        saveState: Boolean = false,
    )
}
