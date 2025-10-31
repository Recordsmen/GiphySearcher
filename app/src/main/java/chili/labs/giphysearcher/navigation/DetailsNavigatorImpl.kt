package chili.labs.giphysearcher.navigation

import chili.labs.giphysearcher.navigator.AppNavigator
import chili.labs.giphysearcher.screens.details.DetailsNavigator
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DetailsNavigatorImpl @Inject constructor(
    private val navigator: AppNavigator,
) : DetailsNavigator {
    override fun navigateBack() {
        navigator.navigateUp()
    }
}