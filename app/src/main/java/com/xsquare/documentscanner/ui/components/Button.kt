package com.xsquare.documentscanner.ui.components

import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xsquare.documentscanner.R

/**
 * Created by Rajath on 04/04/25.
 */

@Composable
fun IconButton(
    icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}