package com.example.gpsalarm.alarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Vibrator

class VibrationService : Service() {
    var vibration: Vibrator? = null

    override fun onCreate() {
        var vibration = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startVibration(vibration!!)
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent): IBinder {
        return MyBinder()

    }
    fun startVibration(vibration: Vibrator){
       // var pattern: Array<Long> = arrayOf(2000)
        vibration.vibrate(6000)
    }
    fun stopVibration(){
        vibration!!.cancel()

    }
   inner class MyBinder: Binder(){
        fun getService():VibrationService = this@VibrationService
    }
}