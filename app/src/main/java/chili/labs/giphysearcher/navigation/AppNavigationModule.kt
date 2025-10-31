package chili.labs.giphysearcher.navigation

import chili.labs.giphysearcher.navigation.args.DetailsArgsImpl
import chili.labs.giphysearcher.screens.details.DetailsArgs
import chili.labs.giphysearcher.screens.details.DetailsNavigator
import chili.labs.giphysearcher.screens.home.HomeNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppNavigationModule {

    @Binds
    @ViewModelScoped
    abstract fun homeNavigator(navigator: HomeNavigatorImpl): HomeNavigator

    @Binds
    @ViewModelScoped
    abstract fun detailsNavigator(navigator: DetailsNavigatorImpl): DetailsNavigator

    @Binds
    @ViewModelScoped
    abstract fun detailsArgs(args: DetailsArgsImpl): DetailsArgs
}
