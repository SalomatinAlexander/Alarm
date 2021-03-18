package com.example.gpsalarm.database

import android.provider.BaseColumns
import java.sql.Types.INTEGER

object MyConstance {

    const val COUNT_NAME = "count_name"
    const val COUNT_HOURS = "count_hours"
    const val COUNT_MINUTES = "count_minutes"
    const val COUNT_STATUS = "count_status"


    const val TABLE_NAME = "my_table"
    const val DB_NAME = "my_db.db"
    const val VERSION = 2

    const val TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ("+"${BaseColumns._ID} INTEGER PRIMARY KEY,"+
    " ${COUNT_NAME} TEXT, "+" ${COUNT_HOURS} INTEGER,"+" ${COUNT_MINUTES} INTEGER,"+" ${COUNT_STATUS} INTEGER)"

    const val DROP_TABLE =  "DROP TABLE IF EXISTS $TABLE_NAME"


}

