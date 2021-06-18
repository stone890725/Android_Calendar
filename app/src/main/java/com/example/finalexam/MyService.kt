package com.example.finalexam

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import java.util.*
import kotlin.concurrent.schedule



class MyService : Service() {
    private val mBinder: MyBinder = MyBinder()
    lateinit var manager: NotificationManager
    lateinit var builder : Notification.Builder
    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val db = SqliteFunc(this)
        Timer().schedule(0,2000){
            var sql = "SELECT Title,Content,id FROM Schedule WHERE NotificationTime='"+DateUtil.nowDateTime+"' AND IsFinish='"+0+"' AND IsNotification ='0'"
            val cursor = db.Select(sql)
            cursor.moveToFirst()
       //     System.out.println("DateUtil.nowDate="+DateUtil.nowDateTime)
            //System.out.println("cursor.count="+cursor.count)
            if(cursor.count!=0) {
                for (i in 0 until cursor.count) {
                    var update = "UPDATE `Schedule` SET IsNotification='1' WHERE id='${cursor.getString(2)}'"
                    db.execUd(update)
                    val channel = NotificationChannel(i.toString(), "通知", NotificationManager.IMPORTANCE_HIGH)
                    noti(cursor.getString(0),cursor.getString(1))
                    manager.notify(i, builder.build())
                    cursor.moveToNext()
                }

            }

        }
        // 執行任務
        return Service.START_STICKY
    }


    fun noti(title: String,content: String) {
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("1", title, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            builder = Notification.Builder(this, "1")
        } else {
            builder = Notification.Builder(this)
        }

        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            .setAutoCancel(true)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }


    internal inner class MyBinder : Binder() {
        fun startDownload() {

            // 執行任務
        }
    }


    companion object {
        const val TAG = "MyService"
    }
}