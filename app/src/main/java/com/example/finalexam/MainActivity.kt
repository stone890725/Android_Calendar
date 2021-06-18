package com.example.finalexam

import DateUtil
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var items : ArrayList<String> = ArrayList()  //定義資料清單
    private var id : ArrayList<String> = ArrayList()
    lateinit var manager: NotificationManager
    lateinit var builder : Notification.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "行事曆"



        var serviceIntent = Intent(this,MyService::class.java)
        startService(serviceIntent)

        val listView = findViewById<ListView>(R.id.list_view)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")


        val db = SqliteFunc(this)
        db.CreateTable()



        val btn_Date:Button = findViewById(R.id.button_date)

        val btn_AddOneDate = findViewById<ImageButton>(R.id.button_AddDay)
        val btn_SubOneDate = findViewById<ImageButton>(R.id.button_SubDay)
        val btn_test = findViewById<Button>(R.id.button5)



        val btn_Insert = findViewById<Button>(R.id.button_new)


        val cal = Calendar.getInstance()
        var day = cal.get(Calendar.DATE)
        var month = cal.get(Calendar.MONTH)
        var year =cal.get(Calendar.YEAR)

        btn_Date.text=DateUtil.nowDate
        //noti()

        var sql = "SELECT Title,id FROM `Schedule` WHERE date(Time)='"+DateUtil.nowDate+"' AND IsFinish='"+0+"' ORDER BY Time ASC"
        val cursor = db.Select(sql)
        cursor.moveToFirst()
        if(cursor.count!=0) {
            for (i in 0 until cursor.count) {
                items.add("${cursor.getString(0)}")
                id.add(cursor.getString(1))
                cursor.moveToNext()
            }
            cursor.close()
            val adapter = MyAdapter(items)
            listView.setAdapter(adapter)
            //ListView item點擊事件
            listView.setOnItemClickListener(OnItemClickListener { adapterView, view, index, l ->
                //Toast.makeText(this@MainActivity, "我是item點擊事件 i = " + i + "l = " + l, Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Schedule_Detail::class.java)
                System.out.println("id[index]=" + id[index])

                intent.putExtra("ID", id[index])
                startActivity(intent)
            })
        }

        //get a calendar
        btn_Date.setOnClickListener{
            DatePickerDialog(this, { _, year, month, day ->
                run {
                    val format = "${setDateFormat(year, month, day)}"
                    btn_Date.text = format


                    var sql = "SELECT Title,id FROM `Schedule` WHERE date(Time)='"+btn_Date.text+"' AND IsFinish='"+0+"' ORDER BY Time ASC"
                    val cursor = db.Select(sql)
                    cursor.moveToFirst()
                    items.clear()
                    var adapter = MyAdapter(items)
                    listView.setAdapter(adapter)
                    listView.deferNotifyDataSetChanged()

                    if(cursor.count!=0) {
                        for (i in 0 until cursor.count) {
                            items.add("${cursor.getString(0)}")
                            id.add(cursor.getString(1))
                            cursor.moveToNext()
                        }
                        cursor.close()
                        adapter = MyAdapter(items)
                        listView.setAdapter(adapter)
                        //ListView item點擊事件
                        System.out.println("TestDATE")
                        listView.setOnItemClickListener(OnItemClickListener { adapterView, view, index, l ->
                            //Toast.makeText(this@MainActivity, "我是item點擊事件 i = " + i + "l = " + l, Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, Schedule_Detail::class.java)
                            System.out.println("id[index]=" + id[index])

                            intent.putExtra("ID", id[index])
                            startActivity(intent)
                        })
                    }


                }
            }, year, month, day).show()





        }

        btn_AddOneDate.setOnClickListener{
            var addOneDate = dateFormat.parse(btn_Date.text.toString())
            addOneDate=addDate(addOneDate, 1)
            btn_Date.text=dateFormat.format(addOneDate).toString()



            var sql = "SELECT Title,id FROM `Schedule` WHERE date(Time)='"+btn_Date.text+"' AND IsFinish='"+0+"' ORDER BY Time ASC"
            val cursor = db.Select(sql)
            cursor.moveToFirst()
            items.clear()
            var adapter = MyAdapter(items)
            listView.setAdapter(adapter)

            listView.deferNotifyDataSetChanged()
            if(cursor.count!=0) {
                for (i in 0 until cursor.count) {
                    items.add("${cursor.getString(0)}")
                    id.add(cursor.getString(1))
                    cursor.moveToNext()
                }
                cursor.close()
                adapter = MyAdapter(items)
                listView.setAdapter(adapter)
                //ListView item點擊事件
                listView.setOnItemClickListener(OnItemClickListener { adapterView, view, index, l ->
                    //Toast.makeText(this@MainActivity, "我是item點擊事件 i = " + i + "l = " + l, Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, Schedule_Detail::class.java)
                    System.out.println("id[index]=" + id[index])

                    intent.putExtra("ID", id[index])
                    startActivity(intent)
                })
            }


        }

        btn_SubOneDate.setOnClickListener{
            var subOneDate = dateFormat.parse(btn_Date.text.toString())
            subOneDate=addDate(subOneDate, -1)
            btn_Date.text=dateFormat.format(subOneDate).toString()


            var sql = "SELECT Title,id FROM `Schedule` WHERE date(Time)='"+btn_Date.text+"' AND IsFinish='"+0+"'ORDER BY Time ASC"
            val cursor = db.Select(sql)
            cursor.moveToFirst()
            items.clear()
            var adapter = MyAdapter(items)
            listView.setAdapter(adapter)

            listView.deferNotifyDataSetChanged()
            if(cursor.count!=0) {
                for (i in 0 until cursor.count) {
                    items.add("${cursor.getString(0)}")
                    id.add(cursor.getString(1))
                    cursor.moveToNext()
                }
                cursor.close()
                adapter = MyAdapter(items)
                listView.setAdapter(adapter)
                //ListView item點擊事件
                listView.setOnItemClickListener(OnItemClickListener { adapterView, view, index, l ->
                    //Toast.makeText(this@MainActivity, "我是item點擊事件 i = " + i + "l = " + l, Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, Schedule_Detail::class.java)
                    System.out.println("id[index]=" + id[index])

                    intent.putExtra("ID", id[index])
                    startActivity(intent)
                })
            }
        }

        btn_Insert.setOnClickListener{
            startActivity(Intent(this, InsertSchedule::class.java))
        }

        btn_test.setOnClickListener{
            startActivity(Intent(this, IsFinishActivity::class.java))
        }



    }


    fun noti() {
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1", "Day15", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            builder = Notification.Builder(this, "1")
        } else {
            builder = Notification.Builder(this)
        }

        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Day15")
            .setContentText("Day15 Challenge")
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            .setAutoCancel(true)
    }


    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        if(month+1<10){
            return "$year-0${month+1}-$day"

        }
        else {
            return "$year-${month+1}-$day"
        }
    }

    fun addDate(date: Date, day: Int): Date {
        var day = day
        var time = date.time // 得到指定日期的毫秒數
        day = day * 24 * 60 * 60 * 1000 // 要加上的天數轉換成毫秒數
        time += day // 相加得到新的毫秒數
        return Date(time) // 將毫秒數轉換成日期
    }

}
