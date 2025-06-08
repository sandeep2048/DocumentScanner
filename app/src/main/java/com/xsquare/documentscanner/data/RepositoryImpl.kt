package com.xsquare.documentscanner.data

import com.xsquare.documentscanner.data.local.LocalDataSource
import com.xsquare.documentscanner.data.local.entity.Document
import javax.inject.Inject

/**
 * Created by Rajath on 04/04/25.
 */
class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): IRepository {

    override suspend fun saveDocument(document: Document) {
        localDataSource.insert(document)
    }

    override suspend fun getDocuments(): List<Document> {
        return localDataSource.fetchAll()
    }

}