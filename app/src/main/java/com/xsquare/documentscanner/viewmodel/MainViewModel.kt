package com.xsquare.documentscanner.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xsquare.documentscanner.data.IRepository
import com.xsquare.documentscanner.data.local.entity.Document
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rajath on 04/04/25.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IRepository
): ViewModel() {

    private val _docState: MutableState<DocState> = mutableStateOf(DocState.EmptyState)
    val docState: State<DocState> = _docState

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun loadDocs() {
        viewModelScope.launch(Dispatchers.IO) {
            val docs = repository.getDocuments()
            if (docs.isNotEmpty()) {
                _docState.value = DocState.DocumentsAvailable(docs)
            } else {
                _docState.value = DocState.EmptyState
            }
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun onDocumentScanned(document: Document) {
        viewModelScope.launch {
            repository.saveDocument(document)
            loadDocs()
        }
    }

    fun onDocumentScanCancelled() {

    }

    fun onDocumentScanFailed() {

    }

    suspend fun getDocument(id: Long): Document? {
        return repository.getDocument(id)
    }

}