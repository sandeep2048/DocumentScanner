package com.xsquare.documentscanner.di

import android.content.Context
import com.xsquare.documentscanner.data.IRepository
import com.xsquare.documentscanner.data.RepositoryImpl
import com.xsquare.documentscanner.data.local.AppDatabase
import com.xsquare.documentscanner.data.local.LocalDataSource
import com.xsquare.documentscanner.data.local.dao.DocumentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rajath on 05/04/25.
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideDocumentDao(database: AppDatabase): DocumentDao {
        return database.documentDao()
    }

    @Provides
    fun provideRepository(localDataSource: LocalDataSource): IRepository {
        return RepositoryImpl(localDataSource)
    }

}