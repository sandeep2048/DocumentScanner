package com.xsquare.documentscanner.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.xsquare.documentscanner.R
import com.xsquare.documentscanner.navigation.Screen
import com.xsquare.documentscanner.utils.LocalNavController

/**
 * Created by Rajath on 03/04/25.
 */

@Composable
fun TopBar() {
    val navController = LocalNavController.current

    val backStackEntry = navController.currentBackStackEntryAsState()

    val topBarData = getTopBarData(backStackEntry.value)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textData = topBarData.textData
        Text(
            text = stringResource(R.string.app_name),
            style = textData.style,
            color = MaterialTheme.colorScheme.primary,
            textAlign = textData.alignment,
            modifier = Modifier.weight(1f)
        )
        if(topBarData.showSettings) {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "settings"
            )
        }
    }
}

@Composable
private fun getTopBarData(backStackEntry: NavBackStackEntry?): TopBarData {
    val destination = backStackEntry?.destination
    return when {
        destination == null || destination.route == Screen.WelcomeScreen.route -> TopBarData(
            textData = TextData(
                alignment = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            ),
            showSettings = false
        )
        else -> TopBarData(
            textData = TextData(
                alignment = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            ),
            showSettings = true
        )
    }
}

internal data class TopBarData(
    val textData: TextData,
    val showSettings: Boolean
)

internal data class TextData(
    val alignment: TextAlign,
    val style: TextStyle
)