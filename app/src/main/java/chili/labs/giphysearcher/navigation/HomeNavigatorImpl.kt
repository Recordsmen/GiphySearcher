package chili.labs.giphysearcher.navigation

import chili.labs.giphysearcher.navigator.AppNavigator
import chili.labs.giphysearcher.screens.home.HomeNavigator
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HomeNavigatorImpl @Inject constructor(
    private val navigator: AppNavigator,
) : HomeNavigator {

    override fun navigateToDetails(gifId: String) {
        navigator.navigate(Screen.Details.withGifId(gifId))
    }
}