package com.rriviere.f2pcatalog.ui.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rriviere.f2pcatalog.ui.details.GameDetails
import com.rriviere.f2pcatalog.ui.games.Games

@Composable
fun F2PMainScreen() {
    val navController = rememberNavController()

    val colors = MaterialTheme.colorScheme

    var statusBarColor by remember { mutableStateOf(colors.primaryContainer) }
    var navigationBarColor by remember { mutableStateOf(colors.primaryContainer) }

    val animatedStatusBarColor by animateColorAsState(
        targetValue = statusBarColor,
        animationSpec = tween(), label = "animatedStatusBarColor"
    )
    val animatedNavigationBarColor by animateColorAsState(
        targetValue = navigationBarColor,
        animationSpec = tween(), label = "animateColorAsState"
    )

    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            Games(
                viewModel = hiltViewModel(),
                selectGame = {
                    navController.navigate("${NavScreen.GameDetails.route}/$it")
                }
            )

            LaunchedEffect(Unit) {
                statusBarColor = colors.primaryContainer
                navigationBarColor = colors.primaryContainer
            }
        }
        composable(
            route = NavScreen.GameDetails.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.GameDetails.argument0) { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val gameId =
                backStackEntry.arguments?.getLong(NavScreen.GameDetails.argument0) ?: return@composable

            GameDetails(
                viewModel = hiltViewModel(),
                selectGame = {
                    navController.navigate("${NavScreen.GameDetails.route}/$it")
                }
            )

            LaunchedEffect(Unit) {
                statusBarColor = Color.Transparent
                navigationBarColor = colors.background
            }
        }
    }

    LaunchedEffect(animatedStatusBarColor, animatedNavigationBarColor) {
        statusBarColor = animatedStatusBarColor
        navigationBarColor = animatedNavigationBarColor
    }
}

sealed class NavScreen(val route: String) {

    data object Home : NavScreen("Home")

    data object GameDetails : NavScreen("GameDetails") {

        const val routeWithArgument: String = "GameDetails/{gameId}"

        const val argument0: String = "gameId"
    }
}
