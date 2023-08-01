package com.example.guru2_kjy.data.repository

import com.example.guru2_kjy.data.model.Travel
import com.example.guru2_kjy.local.dao.ToDoListDao
import com.example.guru2_kjy.local.dao.TravelDao
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class TravelRepository(private val travelDao: TravelDao, private val toDoListDao: ToDoListDao) {

    fun getTravels() = flow {
        travelDao.getTravels().collect {
            emit(it)
        }
    }.catch {
        emit(emptyList())
    }

    fun getTravel(nid:Long) = flow {
        travelDao.getTravel(nid).collect {
            emit(it)
        }
    }.catch {
        emit(Travel.TravelItem())
    }

    fun getToDoList(travelNid: Long) = flow {
        toDoListDao.getToDoList(travelNid).collect {
            emit(it)
        }
    }.catch {
        emit(emptyList())
    }

    suspend fun insertTravel(travelItem: Travel.TravelItem): Long {
        return travelDao.insertReplace(travelItem)
    }

    suspend fun insertToDoList(item: Travel.TravelToDoList):Long {
        return toDoListDao.insertReplace(item)
    }

    suspend fun deleteTravel(nid: Long) {
        travelDao.deleteTravel(nid)
        toDoListDao.deleteTravel(nid)
    }

    suspend fun deleteAll() {
        travelDao.deleteAll()
        toDoListDao.deleteAll()
    }
}