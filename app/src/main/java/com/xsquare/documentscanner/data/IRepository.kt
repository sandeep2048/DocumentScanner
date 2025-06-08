package com.xsquare.documentscanner.data

import com.xsquare.documentscanner.data.local.entity.Document

/**
 * Created by Rajath on 04/04/25.
 */
interface IRepository {

    suspend fun saveDocument(document: Document)

    suspend fun getDocuments(): List<Document>

    suspend fun getDocument(id: Long): Document?

}