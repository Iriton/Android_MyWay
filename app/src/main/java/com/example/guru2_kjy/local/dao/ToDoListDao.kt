package com.example.guru2_kjy.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.guru2_kjy.data.model.Travel
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertReplace(item: Travel.TravelToDoList): Long

    @Query("SELECT * FROM tbTravelToDoList WHERE travelNid = :travelNid")
    fun getToDoList(travelNid: Long): Flow<List<Travel.TravelToDoList>>

    @Query("DELETE FROM tbTravelToDoList WHERE travelNid = :travelNid")
    suspend fun deleteTravel(travelNid: Long)

    @Query("DELETE FROM tbTravelToDoList")
    suspend fun deleteAll()
}