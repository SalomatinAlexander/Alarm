package com.example.gpsalarm.database

import android.app.AlarmManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build.ID
import android.provider.BaseColumns
import android.util.Log
import com.example.gpsalarm.OptionActivity

class AlarmDbManager(_context: Context) {
    var context = _context


    var dbHelper = AlarmDbHelper(context)
    var sql: SQLiteDatabase? = null

    fun OpenDb() {
        Log.d(OptionActivity.TAG, "OpenDb: ")
        sql = dbHelper.writableDatabase
    }

    fun insertDb(name: String, hours: Int, minutes: Int) {
        var cv = ContentValues()
        cv.put(MyConstance.COUNT_NAME, name)
        cv.put(MyConstance.COUNT_HOURS, hours)
        cv.put(MyConstance.COUNT_MINUTES, minutes)
        cv.put(MyConstance.COUNT_STATUS, 1)
        sql?.insert(MyConstance.TABLE_NAME, null, cv)
    }

    fun getFromDb(): ArrayList<AlarmList> {

        var list = ArrayList<AlarmList>()
        var cursor = sql?.query(MyConstance.TABLE_NAME, null, null, null, null, null, null)
        while (cursor?.moveToNext()!!) {
            var alarmList = AlarmList()
            var name = cursor.getString(cursor.getColumnIndex(MyConstance.COUNT_NAME))
            var hours = cursor.getInt(cursor.getColumnIndex(MyConstance.COUNT_HOURS))
            var minutes = cursor.getInt(cursor.getColumnIndex(MyConstance.COUNT_MINUTES))
            var status = cursor.getInt(cursor.getColumnIndex(MyConstance.COUNT_STATUS))
            var id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            alarmList.title = name
            alarmList.hours = hours
            alarmList.minutes = minutes
            alarmList.status = status
            alarmList.id = id
            list.add(alarmList)
        }


        cursor.close()

        return list

    }
    fun changeStatus(id: Int, status: Int){
        sql?.execSQL("UPDATE "+ MyConstance.TABLE_NAME + " SET "+ MyConstance.COUNT_STATUS+ "=${status} WHERE " + BaseColumns._ID+" like ${id}")



    }


    fun removeItem(id: String) {
        val selection = BaseColumns._ID + "=$id"
        sql?.delete(MyConstance.TABLE_NAME, selection, null)



    }

    fun closeDb() {
        dbHelper.close()

    }

}