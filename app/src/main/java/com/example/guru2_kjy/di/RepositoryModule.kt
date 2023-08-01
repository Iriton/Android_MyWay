package com.example.guru2_kjy.di

import com.example.guru2_kjy.data.repository.LoginRepository
import com.example.guru2_kjy.data.repository.TravelRepository
import com.example.guru2_kjy.datastore.PreferencesService
import com.example.guru2_kjy.local.dao.ToDoListDao
import com.example.guru2_kjy.local.dao.TravelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


/*
* ViewModel 의 생명주기를 따라가는 모듈
* 뷰모델이 파괴 되면 같이 파괴됨
* */

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    //ViewModel의 생명주기를 바라보는 Scoped
    @ViewModelScoped
    @Provides
    fun provideLoginRepository(
        preferencesService: PreferencesService
    ): LoginRepository {
        return LoginRepository(preferencesService)
    }

    //ViewModel의 생명주기를 바라보는 Scoped
    @ViewModelScoped
    @Provides
    fun provideTravelRepository(
        travelDao: TravelDao,
        toDoListDao: ToDoListDao
    ): TravelRepository {
        return TravelRepository(travelDao, toDoListDao)
    }
}