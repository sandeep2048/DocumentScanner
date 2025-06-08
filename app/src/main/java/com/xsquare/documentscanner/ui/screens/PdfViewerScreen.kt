package com.xsquare.documentscanner.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.xsquare.documentscanner.viewmodel.MainViewModel
import java.io.File

@Composable
fun PdfViewerScreen(documentId: Long, viewModel: MainViewModel) {
    var docPath by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(documentId) {
        docPath = viewModel.getDocument(documentId)?.path
    }

    docPath?.let { path ->
        AndroidView(factory = { context ->
            PDFView(context, null).apply {
                fromFile(File(path)).load()
            }
        }, update = { pdfView ->
            pdfView.fromFile(File(path)).load()
        }, modifier = Modifier)
    }
}
