package com.xsquare.documentscanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.xsquare.documentscanner.R
import com.xsquare.documentscanner.data.local.entity.Document
import com.xsquare.documentscanner.utils.timestampToDate

/**
 * Created by Rajath on 05/04/25.
 */

@Composable
fun DocumentCard(
    document: Document,
    onDocClick: (Document) -> Unit,
    onDocSettingsClick: (Document) -> Unit
) {
    Column {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.clickable { onDocClick(document) }
        ) {
            DocumentCardPreview(document)
            DocumentCardDetails(document, onDocSettingsClick)
        }
    }
}

@Composable
private fun DocumentCardPreview(document: Document) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(document.thumbnailUri)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxWidth().height(100.dp)
    )
}

@Composable
private fun DocumentCardDetails(document: Document, onDocSettingsClick: (Document) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        Text(
            text = document.name,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = document.createdAt.timestampToDate(),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_ellipsis),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onDocSettingsClick.invoke(document)
                }
            )
        }

    }
}