package com.example.guru24

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 (user_email을 PRIMARY KEY로 설정)
        db.execSQL(
            "CREATE TABLE DB_login (" +
                    "user_email TEXT PRIMARY KEY," + // PK 설정
                    "user_number INTEGER," +
                    "user_password TEXT)" // user_password 컬럼 추가
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 기존 테이블 삭제 후 재생성
        db.execSQL("DROP TABLE IF EXISTS DB_login")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "Login.db"
        private const val DATABASE_VERSION = 2 // 버전 업데이트
    }
}
