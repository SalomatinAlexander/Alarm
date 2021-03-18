 package com.example.gpsalarm.recycler

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.Edits
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gpsalarm.R
import com.example.gpsalarm.database.AlarmDbManager
import com.example.gpsalarm.database.AlarmList
import java.lang.IndexOutOfBoundsException

class AdapterForMainRecycler(_context: Context, _list: ArrayList<AlarmList>) :
    RecyclerView.Adapter<AdapterForMainRecycler.MyHolderForMainRecycler>() {
    var context = _context
    var list = _list


    companion object{


    }


    class MyHolderForMainRecycler(_itemView: View) : RecyclerView.ViewHolder(_itemView) {
        var editTxt = itemView.findViewById<TextView>(R.id.item_txt)
        var timeTxt = itemView.findViewById<TextView>(R.id.time_txt)
        var onBtn = itemView.findViewById<ImageButton>(R.id.on_or_off_image_btn)
        var dbManager = AlarmDbManager(itemView.context)

        fun setData(position: Int) {
            dbManager.OpenDb()

            if(dbManager.getFromDb().get(position).status == 1) {
                onBtn.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_alarm_on_24))
            }else{
                onBtn.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_alarm_off_24))
            }
            onBtn.setOnClickListener(View.OnClickListener {
                OnOrOff(dbManager.getFromDb().get(position),dbManager.getFromDb().get(position).status )

            })

            var minStr: String? = null
            var hourStr: String? = null
            var minInt = dbManager.getFromDb().get(position).minutes
            var hourInt = dbManager.getFromDb().get(position).hours

            if (minInt < 10) {
                minStr = "0${minInt.toString()}"
            } else {
                minStr = minInt.toString()
            }
            if (hourInt < 10) {
                hourStr = "0${hourInt.toString()}"
            } else {
                hourStr = hourInt.toString()
            }

            editTxt.setText(dbManager.getFromDb().get(position).title)
            timeTxt.setText("${hourStr}:${minStr}")
        }
        fun OnOrOff(list: AlarmList, state: Int) {
            when(state){
                0 -> {
                    onBtn.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_alarm_on_24))
                    dbManager.changeStatus(list.id, 1)
                }
                1 -> {
                    onBtn.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_baseline_alarm_off_24))
                    dbManager.changeStatus(list.id, 0)



            }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderForMainRecycler {

        var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_for_main_recycler, parent, false)
        return MyHolderForMainRecycler(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolderForMainRecycler, position: Int) {
        return holder.setData(position)
    }

    fun deletedItem(position: Int) {
        list.removeAt(position)
        notifyItemRangeChanged(0, list.size)
        notifyItemRemoved(position)

    }

    fun upDate(newList: ArrayList<AlarmList>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


}