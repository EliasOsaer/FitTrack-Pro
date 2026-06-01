package be.howest.eliasosaer.fittrackpro.ui.navigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun FitTrackBottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    NavigationBar {
        val cartItemCount = 0

        bottomBarScreens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(stringResource(screen.labelResourceId))
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (cartItemCount > 0 && screen.route == Screen.Shop.route) {
                                Badge {
                                    Text(cartItemCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = stringResource(screen.labelResourceId)
                        )
                    }
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}