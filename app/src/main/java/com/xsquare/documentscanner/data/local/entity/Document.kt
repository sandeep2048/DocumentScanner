package com.xsquare.documentscanner.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Rajath on 05/04/25.
 */
@Entity(tableName = "document")
data class Document(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Long = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("path") val path: String,
    @ColumnInfo("size") val size: Long,
    @ColumnInfo("created_timestamp") val createdAt: Long,
    @ColumnInfo("modified_timestamp") val modifiedAt: Long? = null,
    @ColumnInfo("thumbnail_uri") val thumbnailUri: String? = null
)