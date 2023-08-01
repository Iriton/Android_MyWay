package com.example.guru2_kjy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

sealed class Travel {

    @Serializable
    @Entity(tableName = "tbTravel")
    data class TravelItem(
        @PrimaryKey(autoGenerate = true) var nid: Long = 0L,
        var areaName: String = "",
        var startDate: String = "",
        var endDate: String = "",
        var imageKey: String = "",
        var calculateTravelDays: Int = 0,
    )

    @Serializable
    @Entity(tableName = "tbTravelToDoList")
    data class TravelToDoList(
        @PrimaryKey(autoGenerate = true) var nid: Long = 0L,
        var travelNid: Long = 0L,
        var place: String = "",
        var todoTime: String = "",
        var transport: String = "",
        var money: String = "",
        var memo: String = "",
        var day: Int = 0
    )
}
