package com.example.gamerockpaperscissors

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "UserDatabase2.db"
        private const val DATABASE_VERSION = 1
        private
        const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTablequery = ("CREATE TABLE $TABLE_NAME ("+
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_SCORE INTEGER)")
        db?.execSQL(createTablequery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUser(username: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_SCORE, 0)
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()

        return userExists
    }

    fun readUserScore(username: String, password: String): Int? {
        val db = readableDatabase
        val columns = arrayOf(COLUMN_SCORE)
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        var score: Int? = null
        if (cursor.moveToFirst()) {
            score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
        }
        cursor.close()

        return score
    }

    fun updateUserScore(username: String, score: Int) {
        val db = readableDatabase
        val dropTableQuery = "UPDATE $TABLE_NAME SET $COLUMN_SCORE = ? WHERE $COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(score, username)
        db?.execSQL(dropTableQuery, selectionArgs)
    }

}