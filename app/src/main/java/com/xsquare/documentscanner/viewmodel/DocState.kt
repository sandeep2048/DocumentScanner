package com.xsquare.documentscanner.viewmodel

import com.xsquare.documentscanner.data.local.entity.Document

/**
 * Created by Rajath on 06/04/25.
 */
sealed class DocState {
    object Loading: DocState()
    object EmptyState : DocState()
    data class DocumentsAvailable(val documents: List<Document>) : DocState()
}