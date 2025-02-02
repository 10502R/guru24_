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
                    "user_password TEXT);"
        )

        db.execSQL(
            "CREATE TABLE DB_student_info (" +
                    "user_email TEXT," +
                    "user_number INTEGER," +
                    "PRIMARY KEY(user_email, user_number))"
        )

        // 🔹 최근 검색어 테이블 추가
        db.execSQL(
            "CREATE TABLE RecentSearches (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "search_text TEXT UNIQUE);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS DB_login")
        db.execSQL("DROP TABLE IF EXISTS DB_student_info")
        db.execSQL("DROP TABLE IF EXISTS RecentSearches") // 🔹 기존 테이블 제거 후 재생성
        onCreate(db)
    }

    // 🔹 학생 정보 저장
    fun insertStudentInfo(email: String, number: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
        }

        try {
            val rowId = db.insert("DB_student_info", null, values)
            if (rowId != -1L) {
                Log.d("DBHelper", "학생 정보 저장 성공!")
            } else {
                Log.d("DBHelper", "학생 정보 저장 실패!")
            }
        } catch (e: Exception) {
            Log.e("DBHelper", "학생 정보 저장 중 오류 발생: ${e.message}")
        } finally {
            db.close()
        }
    }

    // 🔹 학생 정보 존재 여부 확인
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
        return isExist
    }

    // 🔹 로그인 확인
    fun isValidLogin(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            "DB_login",
            arrayOf("user_email", "user_password"),
            "user_email = ? AND user_password = ?",
            arrayOf(email, password),
            null, null, null
        )

        val isExist = cursor.moveToFirst()
        cursor.close()
        db.close()

        return isExist
    }

    // 🔹 최근 검색어 저장 (중복 방지)
    fun insertSearchQuery(query: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("search_text", query)
        }

        try {
            db.insertWithOnConflict("RecentSearches", null, values, SQLiteDatabase.CONFLICT_IGNORE) // 중복 방지
            Log.d("DBHelper", "검색어 저장 성공: $query")
        } catch (e: Exception) {
            Log.e("DBHelper", "검색어 저장 실패: ${e.message}")
        } finally {
            db.close()
        }
    }

    // 🔹 최근 검색어 불러오기 (최신순)
    fun getRecentSearches(): List<String> {
        val db = readableDatabase
        val searchList = mutableListOf<String>()
        val cursor = db.query("RecentSearches", arrayOf("search_text"), null, null, null, null, "id DESC")

        while (cursor.moveToNext()) {
            searchList.add(cursor.getString(0))
        }

        cursor.close()
        db.close()
        return searchList
    }

    // 🔹 특정 검색어 삭제
    fun deleteSearchQuery(query: String) {
        val db = writableDatabase
        db.delete("RecentSearches", "search_text = ?", arrayOf(query))
        db.close()
    }

    // 🔹 전체 검색어 삭제
    fun clearAllSearchQueries() {
        val db = writableDatabase
        db.delete("RecentSearches", null, null)
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "Login.db"
        private const val DATABASE_VERSION = 18 // 🔹 버전 증가 (테이블 변경됨)
    }
}
