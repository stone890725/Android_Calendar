package com.example.finalexam

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class InsertSchedule : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title="新增"

        val btn_insert = findViewById<Button>(R.id.btn_send)
        val btn_date = findViewById<Button>(R.id.btn_date)
        val btn_time = findViewById<Button>(R.id.btn_time)
        val btn_time2 = findViewById<Button>(R.id.btn_time2)
        var btn_cancel = findViewById<Button>(R.id.btn_cancel)


        val et_title = findViewById<EditText>(R.id.et_title)
        val et_content = findViewById<EditText>(R.id.et_content)


        val cal = Calendar.getInstance()
        var day = cal.get(Calendar.DATE)
        var month = cal.get(Calendar.MONTH)
        var year =cal.get(Calendar.YEAR)
        btn_date.text=DateUtil.nowDate


        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minute: Int = cal.get(Calendar.MINUTE)
        btn_time.text = getString(R.string.time , DateUtil.nowHour,DateUtil.nowMin)
        btn_time2.text = getString(R.string.time , DateUtil.nowHour,DateUtil.nowMin)
        var alertHour:Int = hour
        var alertMinute:Int = minute


        et_title.setOnClickListener{
            et_title.setText("")
        }
        et_content.setOnClickListener{
            et_content.setText("")
        }


        btn_date.setOnClickListener{
            DatePickerDialog(this, { _, year, month, day ->
                run {
                    val format = "${setDateFormat(year, month, day)}"
                    btn_date.text = format
                }
            }, year, month, day).show()
        }

        btn_time.setOnClickListener{
            TimePickerDialog(this, { _, hour, minute ->
                btn_time.text = getString(R.string.time , hour,minute)
            }, hour, minute, true).show()
        }

        btn_time2.setOnClickListener{
            TimePickerDialog(this, { _, hour, minute ->
                btn_time2.text = getString(R.string.time ,hour,minute)
                alertMinute = minute
                alertHour=hour
            }, hour, minute, true).show()
        }



        btn_insert.setOnClickListener{

            val date = btn_date.text.toString()+" " + btn_time.text.toString()
            val alertDate =btn_date.text.toString()+" " + btn_time2.text.toString()
            val sdf = SimpleDateFormat("y   yyy-MM-dd HH:mm")

            if (et_title.text.equals("請輸入標題")||et_title.text.equals("")||et_content.text.equals("請輸入內容")||et_content.text.equals("")){
                val toast = Toast.makeText(applicationContext, "請輸入標題與內容", Toast.LENGTH_SHORT)
                toast.show()
                   }
            val datNow = sdf.parse(getNow())
            val datGet = sdf.parse(date)
            val dateAlertDate =sdf.parse(alertDate)

            if (datNow.time > datGet.time){
                val toast = Toast.makeText(applicationContext, "請不要選擇過去時間", Toast.LENGTH_SHORT)
                toast.show()
            }
            else if(datNow.time>dateAlertDate.time){
                val toast = Toast.makeText(applicationContext, "通知時間請選擇未來", Toast.LENGTH_SHORT)
                toast.show()
            }

            else if(datGet.time<dateAlertDate.time){
                val toast = Toast.makeText(applicationContext, "你的通知時間設定錯誤", Toast.LENGTH_SHORT)
                toast.show()
            }

            else{
                val db = SqliteFunc(this)
                val data = db.insert(et_title.text.toString(),et_content.text.toString(),date,0,alertDate)
                val toast = Toast.makeText(applicationContext, data, Toast.LENGTH_SHORT)
                toast.show()
                startActivity(Intent(this, MainActivity::class.java))
            }

        }

        btn_cancel.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }




    }
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        if(month+1<10){
            return "$year-0${month+1}-$day"

        }
        else {
            return "$year-${month+1}-$day"
        }
    }

    fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24){
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())
        }else{
            var tms = Calendar.getInstance()
            return tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH).toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString() + " " + tms.get(Calendar.HOUR_OF_DAY).toString() + ":" + tms.get(Calendar.MINUTE).toString() +":" + tms.get(Calendar.SECOND).toString() +"." + tms.get(Calendar.MILLISECOND).toString()
        }

    }




}