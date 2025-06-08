package com.xsquare.documentscanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xsquare.documentscanner.data.local.entity.Document

/**
 * Created by Rajath on 05/04/25.
 */

@Dao
abstract class DocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(document: Document): Long

    @Query("SELECT * FROM document")
    abstract suspend fun fetchAll(): List<Document>

}