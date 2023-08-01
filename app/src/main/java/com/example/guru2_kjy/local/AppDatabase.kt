package com.example.guru2_kjy.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.guru2_kjy.data.model.Travel
import com.example.guru2_kjy.local.dao.ToDoListDao
import com.example.guru2_kjy.local.dao.TravelDao

@Database(entities = arrayOf(Travel.TravelItem::class, Travel.TravelToDoList::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun travelDao(): TravelDao
    abstract fun todoListDao(): ToDoListDao
}