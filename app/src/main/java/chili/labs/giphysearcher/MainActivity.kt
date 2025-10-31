package chili.labs.giphysearcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import chili.labs.giphysearcher.navigation.Navigation
import chili.labs.giphysearcher.navigator.AppNavigator
import chili.labs.giphysearcher.ui.theme.GiphySearcherTheme
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: Lazy<AppNavigator>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GiphySearcherTheme {
                Navigation(
                  navigator = appNavigator.get()
                )
            }
        }
    }
}