package com.example.gpsalarm

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gpsalarm.database.AlarmDbManager
import com.example.gpsalarm.location.MyLocationListener
import com.example.gpsalarm.recycler.AdapterForMainRecycler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    val REQUEST_PERMISSION = 123
    var mBtn: FloatingActionButton? = null
    var mTxt: TextView? = null
    var adapter: AdapterForMainRecycler? = null
    var dbManager: AlarmDbManager? = null
    companion object{ val SEND_IN_BROADCAST = "SEND_IN_BROADCAST" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        setContentView(R.layout.activity_main)
        var permission =
            ActivityCompat.checkSelfPermission(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION).toString())
        if(permission == PackageManager.PERMISSION_GRANTED){
            var lock = MyLocationListener.SetUpLocationListener(this)
            var imHere = MyLocationListener.imHere
            Toast.makeText(this, "$imHere", Toast.LENGTH_LONG).show()
        }
        else {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION);
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==  PackageManager.PERMISSION_GRANTED){
                var lock = MyLocationListener.SetUpLocationListener(this)
                var imHere = MyLocationListener.imHere
                Toast.makeText(this, "$imHere", Toast.LENGTH_LONG).show()

            }
            }

        mTxt = findViewById(R.id.txt__non_null_main)
        var recycler: RecyclerView = findViewById(R.id.recycler_view_main)
        dbManager = AlarmDbManager(this)
        dbManager?.OpenDb()
        nonNullTxt()




         adapter = AdapterForMainRecycler(this, dbManager!!.getFromDb())
        var linearalManager= LinearLayoutManager(this)
        linearalManager.orientation = RecyclerView.VERTICAL
        recycler.layoutManager = linearalManager
        recycler.adapter = adapter
        var swipe = getSwipe()
        swipe.attachToRecyclerView(recycler)

        mBtn = findViewById(R.id.floatingBtn_main)
        mBtn?.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)

        })
    }
    fun getSwipe(): ItemTouchHelper{
          return  ItemTouchHelper(object : ItemTouchHelper
          .SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
              override fun onMove(recyclerView: RecyclerView,
                                  viewHolder: RecyclerView.ViewHolder,
                                  target: RecyclerView.ViewHolder)
                      :Boolean {
                  return false
              }

              override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  dbManager?.removeItem(dbManager!!.getFromDb()[viewHolder.adapterPosition].id.toString())
                  adapter?.deletedItem(viewHolder.adapterPosition)
                  adapter?.upDate(dbManager!!.getFromDb())
                  nonNullTxt()

              }
        })

    }
    fun nonNullTxt(){
        if(dbManager!!.getFromDb().size != 0){
            mTxt?.visibility = View.GONE

        }else{
            mTxt?.visibility = View.VISIBLE


        }
    }

    override fun onResume() {
        super.onResume()

        nonNullTxt()
    }

}
