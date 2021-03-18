package com.example.gpsalarm

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.example.gpsalarm.alarm.MyAlarm
import com.example.gpsalarm.database.AlarmDbManager
import com.example.gpsalarm.fragment.TimeSetFragment
import java.sql.Time
import java.util.*


class OptionActivity : AppCompatActivity() {
    companion object{
        public var TAG = "TAG"
    }
    var mCloseBtn: ImageButton? = null
    var date: Calendar = Calendar.getInstance()
    var nameTxt: EditText? = null
    var optionBtn: ImageButton? = null
    var dbManager: AlarmDbManager? = null

    var timePicker: TimePicker? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
         dbManager = AlarmDbManager(this)


        Log.d(TAG, "onCreate: ")

        nameTxt = findViewById(R.id.name_txt)
        nameTxt?.setText("Будильник")
        optionBtn = findViewById(R.id.options_btn)
        timePicker = findViewById<TimePicker>(R.id.time_picker)
        timePicker?.setIs24HourView(true)
        timePicker?.setOnTimeChangedListener { timePicker, hours, minutes ->
            var hour = hours
            var minut = minutes

        }

        var mCheckBtn: ImageButton = findViewById(R.id.imBtnCheck)
        mCheckBtn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            insertToDb(dbManager!!)
            startActivity(intent)
        })
        mCloseBtn = findViewById(R.id.imBtnClose)
        mCloseBtn?.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        optionBtn?.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, GSPActivity::class.java)
            startActivity(intent)

        })


    }
    fun insertToDb(dbManager: AlarmDbManager){
        Log.d(TAG, "insertToDb:")
        dbManager.OpenDb()
        dbManager?.insertDb(nameTxt?.text.toString(),timePicker!!.hour, timePicker!!.minute )
        var alarm = MyAlarm()
        alarm.setAlarm(this, timePicker!!.hour, timePicker!!.minute)
    }

    override fun onPause() {
        super.onPause()
        dbManager?.OpenDb()
        Log.d(TAG, "onPause: ")



    }

    override fun onDestroy() {
        super.onDestroy()

    }
}