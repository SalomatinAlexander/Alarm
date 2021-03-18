package com.example.gpsalarm.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable

class AlarmDbHelper(_context: Context) :
        SQLiteOpenHelper(_context, MyConstance.DB_NAME, null, MyConstance.VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(MyConstance.TABLE_STRUCTURE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
      p0?.execSQL(MyConstance.DROP_TABLE)
        onCreate(p0)
    }


}