package com.example.guru24

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE DB_login (" +
                    "user_email TEXT PRIMARY KEY," +
                    "user_number INTEGER," +
                    "user_password TEXT NOT NULL);"
        )

        db.execSQL(
            "CREATE TABLE DB_student_info (" +
                    "user_email TEXT NOT NULL," +
                    "user_number INTEGER PRIMARY KEY);"
        )

        Log.d("DBHelper", "테이블 생성 완료")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS DB_login")
        db.execSQL("DROP TABLE IF EXISTS DB_student_info")
        onCreate(db)
    }

    // 🔹 학생 정보 저장 (중복된 경우 덮어쓰기)
    fun insertStudentInfo(email: String, number: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
        }

        try {
            val rowId = db.insertWithOnConflict("DB_student_info", null, values, SQLiteDatabase.CONFLICT_REPLACE)
            if (rowId != -1L) {
                Log.d("DBHelper", "학생 정보 저장 성공: 이메일=$email, 학번=$number")
            } else {
                Log.e("DBHelper", "학생 정보 저장 실패: 이메일=$email, 학번=$number")
            }
        } catch (e: Exception) {
            Log.e("DBHelper", "학생 정보 저장 중 오류 발생: ${e.message}")
        } finally {
            db.close()
        }
    }

    // 🔹 학생 정보 존재 여부 확인 (조회 결과 로그 추가)
    fun isStudentInfoExist(email: String, number: Int): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            "DB_student_info",
            arrayOf("user_email", "user_number"),
            "user_email = ? AND user_number = ?",
            arrayOf(email, number.toString()),
            null, null, null
        )

        val isExist = cursor.moveToFirst()
        cursor.close()
        db.close()

        Log.d("DBHelper", "학생 정보 조회: 이메일=$email, 학번=$number, 존재여부=$isExist")
        return isExist
    }

    fun isValidLogin(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            "DB_login", // 테이블 이름
            arrayOf("user_email", "user_password"), // 조회할 컬럼
            "user_email = ? AND user_password = ?", // 조건
            arrayOf(email, password), // 조건 값
            null, null, null
        )

        val isValid = cursor.moveToFirst()
        cursor.close()
        db.close()

        return isValid
    }

    // 🔹 회원 정보 저장 (중복된 경우 업데이트)
    fun saveUserData(email: String, number: Int, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
            put("user_password", password)
        }

        return try {
            val rowId = db.insertWithOnConflict("DB_login", null, values, SQLiteDatabase.CONFLICT_REPLACE)
            if (rowId != -1L) {
                Log.d("DBHelper", "회원가입 성공: 이메일=$email, 학번=$number, 비밀번호=$password")
                true
            } else {
                Log.e("DBHelper", "회원 정보 저장 실패: 이메일=$email, 학번=$number")
                false
            }
        } catch (e: Exception) {
            Log.e("DBHelper", "회원 정보 저장 중 오류 발생: ${e.message}")
            false
        } finally {
            db.close()
        }
    }

    companion object {
        private const val DATABASE_NAME = "Login.db"
        private const val DATABASE_VERSION = 23 // 🔹 DB 스키마 변경 시 버전 증가
    }
}
