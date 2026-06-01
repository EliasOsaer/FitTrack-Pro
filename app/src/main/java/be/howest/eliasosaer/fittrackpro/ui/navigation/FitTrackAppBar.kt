package be.howest.eliasosaer.fittrackpro.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import be.howest.eliasosaer.fittrackpro.R

data class FitTrackAppBarAction(
    val icon: ImageVector,
    @StringRes val contentDescriptionResId: Int,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitTrackAppBar(
    modifier: Modifier = Modifier,
    @StringRes appName: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    actions: List<FitTrackAppBarAction> = emptyList()
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(stringResource(appName))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = stringResource(action.contentDescriptionResId)
                    )
                }
            }
        }
    )
}
