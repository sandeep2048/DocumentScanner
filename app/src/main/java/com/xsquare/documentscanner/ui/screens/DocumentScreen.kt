package com.xsquare.documentscanner.ui.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.xsquare.documentscanner.R
import com.xsquare.documentscanner.data.local.entity.Document
import com.xsquare.documentscanner.ui.components.DocumentCard
import com.xsquare.documentscanner.ui.components.IconButton
import com.xsquare.documentscanner.ui.components.ListItem
import com.xsquare.documentscanner.ui.components.SearchBar
import com.xsquare.documentscanner.utils.LocalActivity
import com.xsquare.documentscanner.utils.timestampToDate
import com.xsquare.documentscanner.viewmodel.DocState
import com.xsquare.documentscanner.viewmodel.MainViewModel
import java.io.File

/**
 * Created by Rajath on 03/04/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScreen(
    viewModel: MainViewModel
) {
    val activity = LocalActivity.current

    var launchDocSettingsModal by remember {
        mutableStateOf(false)
    }

    var selectedDocument: Document? by remember {
        mutableStateOf(null)
    }

    val documentScannerClient: GmsDocumentScanner by lazy {
        GmsDocumentScanning.getClient(
            GmsDocumentScannerOptions.Builder()
                .setGalleryImportAllowed(true)
                .setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
                .setScannerMode(SCANNER_MODE_FULL)
                .build())
    }

    val scannerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        val resultCode = activityResult.resultCode
        val result = GmsDocumentScanningResult.fromActivityResultIntent(activityResult.data)
        if (resultCode == Activity.RESULT_OK && result != null) {
            // Use result.pages to access the image URIs
            saveFile(viewModel, result)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            viewModel.onDocumentScanCancelled()
        } else {
            // Notify the user that something wrong happened
            viewModel.onDocumentScanFailed()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadDocs()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .blur(if(launchDocSettingsModal) 24.dp else 0.dp)
    ) {

        when(val state = viewModel.docState.value) {
            DocState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DocState.EmptyState -> {
                NoDocsAvailable()
            }
            is DocState.DocumentsAvailable -> {
                DocsList(viewModel, state.documents) { document ->
                    launchDocSettingsModal = true
                    selectedDocument = document
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                icon = R.drawable.ic_doc,
                text = stringResource(R.string.btn_lable_scan_document)
            ) {
                documentScannerClient.getStartScanIntent(activity)
                    .addOnSuccessListener { sender ->
                        scannerLauncher.launch(IntentSenderRequest.Builder(sender).build())
                    }
                    .addOnFailureListener {
                        viewModel.onDocumentScanFailed()
                    }
            }
        }
    }

    if(launchDocSettingsModal && selectedDocument != null) {
        ModalBottomSheet(
            onDismissRequest = {
                launchDocSettingsModal = false
            },
            scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.7f),
            shape = MaterialTheme.shapes.large,
            dragHandle = null,
            modifier = Modifier.fillMaxSize()
        ) {
            DocSettings(document = selectedDocument!!)
        }
    }
}

@Composable
private fun ColumnScope.NoDocsAvailable() {
    val emptyStateBackground = if(isSystemInDarkTheme()) {
        R.drawable.no_docs_available_light_background
    } else {
        R.drawable.no_docs_available_light_background
    }
    Column(
        modifier = Modifier.fillMaxWidth().weight(1f).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(250.dp).height(200.dp),
            painter = painterResource(emptyStateBackground),
            contentDescription = "There are no scanned documents available"
        )
        Text(
            text = stringResource(R.string.no_docs_available),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun ColumnScope.DocsList(
    viewModel: MainViewModel,
    documents: List<Document>,
    onDocSettingsClick: (document: Document) -> Unit
) {

    val query = viewModel.query.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            SearchBar(
                query = query.value
            ) { newQuery ->
                viewModel.updateQuery(newQuery)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
        ) {
            Text(
                text = "YOUR DOCUMENTS",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(count = documents.size) { idx ->
                DocumentCard(documents[idx], onDocSettingsClick)
            }
        }
    }
}

@Composable
private fun DocSettings(document: Document) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                top = 16.dp,
                end = 12.dp
            )
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(0.85f)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(0.15f)
                )
            }
        }
        item {
            ListItem(
                head = {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                body = {
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            ) {

            }
        }
        item {
            ListItem(
                head = {
                    Icon(
                        painter = painterResource(R.drawable.ic_rename),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                body = {
                    Text(
                        text = "Rename",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            ) {

            }
        }

        item {
            ListItem(
                head = {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                body = {
                    Text(
                        text = "Share",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            ) {

            }
        }

        item {
            ListItem(
                head = {
                    Icon(
                        painter = painterResource(R.drawable.ic_save),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                body = {
                    Text(
                        text = "Save to Files",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            ) {

            }
        }

        item {
            ListItem(
                head = {
                    Icon(
                        painter = painterResource(R.drawable.ic_print),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                body = {
                    Text(
                        text = "Print",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            ) {

            }
        }

        item {
            ListItem(
                head = {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                },
                body = {
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    )
                }
            ) {

            }
        }
    }
}

private fun saveFile(
    viewModel: MainViewModel,
    result: GmsDocumentScanningResult
) {
    val pdf = result.pdf
    val pages = result.pages

    val filePath = pdf?.uri?.path
    val previewImgPath = pages?.firstOrNull()?.imageUri?.path
    if(filePath != null && previewImgPath != null) {
        val file = File(filePath)
        val createdAt = System.currentTimeMillis()
        val createdAtDate = createdAt.timestampToDate()
        val name = "Scan $createdAtDate ${file.name}"
        val document = Document(
            name = name,
            path = filePath,
            size = file.length(),
            thumbnailUri = previewImgPath,
            createdAt = System.currentTimeMillis()
        )
        viewModel.onDocumentScanned(document)
    }
}