package com.xsquare.documentscanner.data.local

import com.xsquare.documentscanner.data.local.dao.DocumentDao
import com.xsquare.documentscanner.data.local.entity.Document
import javax.inject.Inject

/**
 * Created by Rajath on 05/04/25.
 */

class LocalDataSource @Inject constructor(
    private val documentDao: DocumentDao
) {

    suspend fun insert(document: Document) {
        documentDao.insert(document)
    }

    suspend fun fetchAll(): List<Document> {
        return documentDao.fetchAll()
    }

    suspend fun fetchById(id: Long): Document? {
        return documentDao.fetchById(id)
    }

}