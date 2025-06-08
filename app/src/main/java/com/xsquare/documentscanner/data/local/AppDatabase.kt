package com.xsquare.documentscanner.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xsquare.documentscanner.data.local.dao.DocumentDao
import com.xsquare.documentscanner.data.local.entity.Document

/**
 * Created by Rajath on 04/04/25.
 */

@Database(
    entities = [Document::class],
    version = AppDatabase.VERSION
)
abstract class AppDatabase(): RoomDatabase() {

    companion object {

        const val VERSION = 1
        const val DATABASE_NAME = "snapdocs_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun documentDao(): DocumentDao
}