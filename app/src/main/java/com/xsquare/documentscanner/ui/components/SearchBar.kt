package com.xsquare.documentscanner.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xsquare.documentscanner.R
import com.xsquare.documentscanner.ui.theme.LightSearchBarBackground
import com.xsquare.documentscanner.ui.theme.LightSearchPlaceholderTextColor

/**
 * Created by Rajath on 05/04/25.
 */

@Composable
fun SearchBar(
    query: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(100),
        value = query,
        onValueChange = onValueChange,
        placeholder = {
            SearchPlaceholder()
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search_light),
                contentDescription = null
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = LightSearchBarBackground,
            unfocusedContainerColor = LightSearchBarBackground,
            disabledContainerColor = LightSearchBarBackground,
            errorContainerColor = LightSearchBarBackground
        )
    )
}

@Composable
private fun SearchPlaceholder() {
    Text(
        text = "Search Documents",
        style = MaterialTheme.typography.bodyLarge,
        color = LightSearchPlaceholderTextColor
    )
}