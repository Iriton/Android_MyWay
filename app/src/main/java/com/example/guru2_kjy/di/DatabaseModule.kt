package com.example.guru2_kjy.di

import android.content.Context
import androidx.room.Room
import com.example.guru2_kjy.local.AppDatabase
import com.example.guru2_kjy.local.dao.ToDoListDao
import com.example.guru2_kjy.local.dao.TravelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "test.db").build()
    }

    @Singleton
    @Provides
    fun provideTravelDao(database: AppDatabase): TravelDao {
        return database.travelDao()
    }

    @Singleton
    @Provides
    fun provideToDoListDao(database: AppDatabase): ToDoListDao {
        return database.todoListDao()
    }
}