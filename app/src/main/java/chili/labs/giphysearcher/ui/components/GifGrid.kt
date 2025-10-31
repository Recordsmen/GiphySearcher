package chili.labs.giphysearcher.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import chili.labs.giphysearcher.R
import chili.labs.giphysearcher.network.domain.GifModel

@Composable
fun GifGrid(
    lazyPagingItems: LazyPagingItems<GifModel>,
    onGifClick: (String) -> Unit
) {
    val spanAllColumns: LazyGridItemSpanScope.() -> GridItemSpan = {
        GridItemSpan(maxLineSpan)
    }
    val isQueryBlank = (lazyPagingItems.itemCount == 0) && (lazyPagingItems.loadState.refresh is LoadState.NotLoading)
    val refreshError = lazyPagingItems.loadState.refresh is LoadState.Error

    when {
        refreshError -> {
            FullScreenErrorDisplay(
                errorMessage = (lazyPagingItems.loadState.refresh as LoadState.Error).error.localizedMessage,
                onRetry = lazyPagingItems::retry
            )
            return
        }
        lazyPagingItems.loadState.refresh is LoadState.Loading -> {
            FullScreenLoadingIndicator()
            return
        }
        isQueryBlank -> {
            EmptyResultsDisplay(query = if (lazyPagingItems.itemCount == 0) "" else "some-query")
            return
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        items(
            count = lazyPagingItems.itemCount,
            key = { index ->
                val gifId = lazyPagingItems[index]?.id
                gifId?.let { "${it}-${index}" } ?: "placeholder-$index"
            }
        )
        { index ->
            lazyPagingItems[index]?.let { gif ->
                GifGridItem(gif = gif, onClick = onGifClick)
            }
        }

        lazyPagingItems.apply {
            if (loadState.append is LoadState.Loading) {
                item(span = spanAllColumns) {
                    BottomLoadingIndicator()
                }
            }

            if (loadState.append is LoadState.Error) {
                item(span = spanAllColumns) {
                    Button(
                        onClick = lazyPagingItems::retry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(stringResource(R.string.action_loading_more_retry))                    }
                }
            }
        }
    }
}

