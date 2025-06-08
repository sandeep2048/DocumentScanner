package com.xsquare.documentscanner.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 * Created by Rajath on 23/03/25.
 */

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("No LocalActivity provided")
}

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}