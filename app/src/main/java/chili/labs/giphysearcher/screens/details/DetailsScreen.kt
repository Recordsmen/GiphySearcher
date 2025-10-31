package chili.labs.giphysearcher.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import chili.labs.giphysearcher.R
import chili.labs.giphysearcher.intents.onIntent
import chili.labs.giphysearcher.ui.components.FullScreenErrorDisplay
import chili.labs.giphysearcher.ui.components.FullScreenLoadingIndicator
import chili.labs.giphysearcher.ui.components.GifDetailContent
import chili.labs.giphysearcher.ui.components.OfflineWarningBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen() {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val screenState by viewModel.screenState.collectAsState()

    val title = screenState.gif?.title ?: stringResource(R.string.detail_title_default)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { title },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onIntent(DetailsIntent.BackClicked)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_back)
                        )
                    }
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(!screenState.isNetworkAvailable) {
                OfflineWarningBar()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                screenState.isLoading -> {
                    FullScreenLoadingIndicator()
                }

                screenState.error != null -> {
                    FullScreenErrorDisplay(
                        errorMessage = screenState.error,
                        onRetry = {
                            viewModel.onIntent(DetailsIntent.RetryClicked)
                        }
                    )
                }


                screenState.gif != null -> {
                    val localGif = screenState.gif
                    GifDetailContent(gif = localGif!!)
                }

                else -> {
                    Text(
                        text = stringResource(R.string.detail_error_not_found),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}