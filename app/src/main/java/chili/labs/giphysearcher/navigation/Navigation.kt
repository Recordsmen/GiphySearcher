package chili.labs.giphysearcher.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import chili.labs.giphysearcher.navigator.AppNavigator
import chili.labs.giphysearcher.navigator.NavigatorEvent
import chili.labs.giphysearcher.screens.details.DetailsScreen
import chili.labs.giphysearcher.screens.home.HomeScreen
import com.funkymuse.composed.core.stability_wrappers.StableHolder
import com.funkymuse.composed.core.stability_wrappers.asStable

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navigator: AppNavigator,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        homeScreen()
        detailScreen()
    }

    NavHostControllerEvents(navigator, navController.asStable)
}

@Composable
private fun NavHostControllerEvents(
    navigator: AppNavigator,
    navController: StableHolder<NavHostController>,
) {
    LaunchedEffect(navController) {
        navigator.navigatorEvents.collect { event ->
            when (event) {
                is NavigatorEvent.NavigateUp -> navController.item.navigateUp()
                is NavigatorEvent.Navigate -> navController.item.navigate(event.route, event.builder)
                NavigatorEvent.PopCurrentBackStack -> navController.item.popBackStack()
                is NavigatorEvent.PopBackStack -> navController.item.popBackStack(event.route, event.inclusive, event.saveState)
            }
        }
    }
}
private fun NavGraphBuilder.homeScreen() {
    composable(
        route = Screen.Home.route,
    ) {
        HomeScreen()
    }
}

private fun NavGraphBuilder.detailScreen() {
    composable(
        route = Screen.Details.route,
        arguments = listOf(
            navArgument(ScreenArgs.GIF_ID) {
                type = NavType.StringType
                nullable = false
            },
        ),
    ) {
        DetailsScreen()
    }
}
