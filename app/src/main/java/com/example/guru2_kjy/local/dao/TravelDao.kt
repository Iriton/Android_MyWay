package com.example.guru2_kjy.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.guru2_kjy.data.model.Travel
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertReplace(item: Travel.TravelItem): Long

    @Query("SELECT * FROM tbTravel")
    fun getTravels(): Flow<List<Travel.TravelItem>>

    @Query("SELECT * FROM tbTravel WHERE nid = :nid")
    fun getTravel(nid: Long): Flow<Travel.TravelItem?>

    @Query("DELETE FROM tbTravel WHERE nid = :nId")
    suspend fun deleteTravel(nId: Long)

    @Query("DELETE FROM tbTravel")
    suspend fun deleteAll()
}