package com.xsquare.documentscanner.navigation

/**
 * Created by Rajath on 23/03/25.
 */

sealed class Screen(val route: String) {
    data object WelcomeScreen: Screen("welcome_screen")
    data object DocumentScreen: Screen("docs_list_screen")
    data object ShareScreen: Screen("share")
    data object PdfViewer: Screen("view_doc")
}