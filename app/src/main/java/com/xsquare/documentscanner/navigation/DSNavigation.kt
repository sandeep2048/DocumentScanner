package com.xsquare.documentscanner.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.xsquare.documentscanner.ui.screens.DocumentScreen
import com.xsquare.documentscanner.ui.screens.WelcomeScreen
import com.xsquare.documentscanner.ui.screens.PdfViewerScreen
import com.xsquare.documentscanner.utils.LocalNavController
import com.xsquare.documentscanner.viewmodel.MainViewModel

/**
 * Created by Rajath on 23/03/25.
 */

@Composable
fun DSNavigation(viewModel: MainViewModel) {
    val navController = LocalNavController.current

    NavHost(
        navController = navController,
        startDestination = Screen.WelcomeScreen.route
    ) {
        composable(Screen.WelcomeScreen.route) {
            WelcomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Screen.DocumentScreen.route) {
            DocumentScreen(
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.PdfViewer.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val docId = backStackEntry.arguments?.getLong("id") ?: 0L
            PdfViewerScreen(documentId = docId, viewModel = viewModel)
        }
    }
}