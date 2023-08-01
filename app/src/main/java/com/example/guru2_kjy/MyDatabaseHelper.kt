package com.example.guru2_kjy

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyWay_DB.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USER = "User"
        private const val COLUMN_USER_NAME = "userName"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTableQuery = "CREATE TABLE IF NOT EXISTS $TABLE_USER (" +
                "$COLUMN_USER_NAME TEXT PRIMARY KEY" +
                ");"
        db.execSQL(createUserTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 업그레이드 시 호출됩니다.
        // 여기서 데이터베이스 스키마를 수정하는 작업을 처리합니다.
        // 이전 버전에서 새 버전으로 데이터베이스를 업그레이드해야 할 때 사용됩니다.
    }

    // 사용자 정보 저장 메서드
    fun createUserTable(userName: String) {
        val db = writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, userName)

        db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    // userName 조회 메서드
    @SuppressLint("Range")
    fun getUserName(): String? {
        val db = readableDatabase
        val query = "SELECT $COLUMN_USER_NAME FROM $TABLE_USER LIMIT 1"
        val cursor = db.rawQuery(query, null)
        var userName: String? = null

        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
        }

        cursor.close()
        db.close()

        return userName
    }
}
