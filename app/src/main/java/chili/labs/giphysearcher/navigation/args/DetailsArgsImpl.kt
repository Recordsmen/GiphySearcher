package chili.labs.giphysearcher.navigation.args

import androidx.lifecycle.SavedStateHandle
import chili.labs.giphysearcher.navigation.ScreenArgs
import chili.labs.giphysearcher.screens.details.DetailsArgs
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DetailsArgsImpl @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : DetailsArgs {

    override val gifId: String
        get() = savedStateHandle[ScreenArgs.GIF_ID] ?: ""
}
