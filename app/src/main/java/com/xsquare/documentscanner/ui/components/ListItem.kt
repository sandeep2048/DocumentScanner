package com.xsquare.documentscanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Rajath on 06/04/25.
 */

@Composable
fun ListItem(
    head: @Composable () -> Unit = {},
    body: @Composable () -> Unit = {},
    tail: @Composable () -> Unit = {},
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                onClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        head()
        Spacer(modifier = Modifier.weight(1f))
        body()
        Spacer(modifier = Modifier.weight(1f))
        tail()
    }
}