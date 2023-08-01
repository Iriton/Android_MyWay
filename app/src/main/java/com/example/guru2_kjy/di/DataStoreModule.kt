package com.example.guru2_kjy.di

import android.content.Context
import com.example.guru2_kjy.datastore.PrefDataStore
import com.example.guru2_kjy.datastore.PrefDataStoreService
import com.example.guru2_kjy.datastore.Preferences
import com.example.guru2_kjy.datastore.PreferencesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/*
* 앱 생성시 생성 되는 모듈
*
* */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providePrefDataStore(@ApplicationContext app: Context): PrefDataStoreService =
        PrefDataStore(app)

    @Singleton
    @Provides
    fun providePreferences(
        preferenceService: PrefDataStoreService
    ): PreferencesService = Preferences(preferenceService)
}