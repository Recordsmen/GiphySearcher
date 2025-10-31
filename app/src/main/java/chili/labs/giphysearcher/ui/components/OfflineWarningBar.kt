package chili.labs.giphysearcher.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import chili.labs.giphysearcher.R // Assuming R.string.warning_offline is defined

@Composable
fun OfflineWarningBar() {
    val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()
    Snackbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = navigationBarPadding.calculateBottomPadding()),
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError
    ) {
        Text(stringResource(R.string.network_your_are_offline))
    }
}