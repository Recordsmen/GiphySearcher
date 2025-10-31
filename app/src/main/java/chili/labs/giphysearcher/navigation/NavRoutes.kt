package chili.labs.giphysearcher.navigation

import androidx.annotation.Keep
import chili.labs.giphysearcher.navigation.ScreenArgs.GIF_ID

sealed class Screen(open val route: String) {
    data object Home : Screen(ScreensRoute.HOME.name)
    data object Details : Screen("${ScreensRoute.DETAILS.name}/{$GIF_ID}") {
        fun withGifId(
            gifId: String,
        ) = "${ScreensRoute.DETAILS.name}/$gifId"
    }
}

object ScreenArgs {
    const val GIF_ID = "gif_id"
}

@Keep
enum class ScreensRoute {
    HOME,
    DETAILS
}
