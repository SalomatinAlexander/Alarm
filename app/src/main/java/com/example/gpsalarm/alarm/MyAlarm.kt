package com.example.gpsalarm.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.gpsalarm.activities.RunningActivity
import com.example.gpsalarm.database.AlarmDbManager
import java.util.*

class MyAlarm(): BroadcastReceiver() {

    companion object {
        var CHANNAL_ID = "12345"
        var NOTIFY_ID = 123
        var hours = "hours"
        var minutes = "minutes"
        var TAG3 = "tag3"

    }

    override fun onReceive(context: Context, intent: Intent?) {
        var hours = intent!!.getIntExtra(hours, 0)
        var minutes = intent!!.getIntExtra(minutes, 0)
        var dbManager = AlarmDbManager(context)
        dbManager.OpenDb()
        var sum = dbManager.getFromDb().size
        Log.d(TAG3, "onReceive: on while")
        var i = 0
        while (sum > i) {
            if ((dbManager.getFromDb()[i].minutes == minutes)
                    && (dbManager.getFromDb()[i].hours == hours) && (dbManager.getFromDb()[i].status == 1)) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show()
                var intent = Intent(context, RunningActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
            i+=1
        }
    }


    fun setAlarm(context: Context, hour: Int, minute: Int) {
        Log.d(TAG3, "setAlarm: ")
        var alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, MyAlarm::class.java)
        intent.putExtra(minutes, minute)
        intent.putExtra(hours, hour)
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        Toast.makeText(context, "min:$minute, hours: $hour", Toast.LENGTH_LONG).show()
        var pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)

    }

    fun cancelAlarm(context: Context) {
        var alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, MyAlarm::class.java)
        var pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.cancel(pi)
    }
}