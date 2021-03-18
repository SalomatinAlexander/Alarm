package com.example.gpsalarm.alarm

import android.app.AlarmManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.IBinder
import com.example.gpsalarm.R
import com.example.gpsalarm.database.AlarmDbManager

class RiseAndShine(_context: Context) {
    var context = _context
    var mediaPlayer = MediaPlayer()
    private lateinit var mService: VibrationService
    private var mBound: Boolean = false

    fun startVibration(){
        var intent = Intent(context, VibrationService::class.java)
        val connection = object: ServiceConnection{

            override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
                val binder = service as VibrationService.MyBinder
                mService = binder.getService()
                mBound = true

            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                TODO("Not yet implemented")
            }

        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)


    }
    fun stopVibration(){
        mService.stopVibration()
        mService.stopSelf()

    }

    fun startAudio(){
         mediaPlayer = MediaPlayer.create(context, R.raw.htc_basic)
        mediaPlayer.start()
    }
    fun stopAudio(){
        mediaPlayer.stop()

    }

}