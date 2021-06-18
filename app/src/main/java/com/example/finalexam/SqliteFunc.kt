package com.example.finalexam

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception
import java.sql.Date
import java.util.*

class SqliteFunc(context: Context) : SQLiteOpenHelper (context, "Schedule"  , null, 1){
    companion object {
        // 資料庫名稱
        val name = "Schedule"
        // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
        val version = 1
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + name)
        onCreate(db)
    }
    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE if not exists ${name} ( id integer PRIMARY KEY autoincrement," +
                "Title varchar(50) not null," +
                "Content varchar(250) not null," +
                "Time datetime not null ," +
                "IsFinish int(1) not null," +
                "IsNotification int(1) not null," +
                "NotificationTime datetime not null"+
                "  )"
        db.execSQL(sql)
    }

    fun CreateTable(){
        val db = this.writableDatabase
        val sql = "CREATE TABLE if not exists ${name} ( id integer PRIMARY KEY autoincrement," +
                "Title varchar(50) not null," +
                "Content varchar(250) not null," +
                "Time datetime not null ," +
                "IsFinish int(1) not null," +
                "IsNotification int(1) not null," +
                "NotificationTime datetime not null"+
                "  )"
        db.execSQL(sql)
    }

    fun dropTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS " + name)
    }

     fun SelectAll():Cursor{
         val db = this.writableDatabase
         return db.rawQuery("SELECT * FROM Schedule",null)
     }

    fun  Select(sql:String):Cursor{
        try {
            val db = this.writableDatabase
           // return "success"
            return db.rawQuery(sql, null)
        }
        catch (e:Exception){
            val db = this.writableDatabase
            System.out.println(e.toString())
            return db.rawQuery("SELECT * FROM Schedule",null)
        }
    }

    fun  exec(sql:String):String{
        try {
            System.out.println("update="+sql)
            val db = this.writableDatabase
            // return "success"
             db.execSQL(sql, null)
            return  "Success update"
        }
        catch (e:Exception){
            System.out.println(e.toString())
            return e.toString()
        }
    }

    fun  execUd(sql:String):String{
        try {
            System.out.println("update="+sql)
            val db = this.writableDatabase
            // return "success"
            db.execSQL(sql)
            return  "Success update"
        }
        catch (e:Exception){
            System.out.println(e.toString())
            return e.toString()
        }
    }


     fun insert(title:String,context: String,time:String,isFinish:Int,notificationTime:String):String {
         try {
             val values = ContentValues()
             values.put("Title",title)
             values.put("Content",context)
             values.put("Time",time)
             values.put("IsNotification",0)
             values.put("IsFinish",isFinish)
             values.put("NotificationTime",notificationTime)
             val db =this.writableDatabase
           //  db.execSQL("Insert into ${name}(Title, Content, Time, IsFinish, NotificationLevel, NotificationTime) VALUES(${title},${context},${time},${isFinish},${notificationLevel},${notificationTime})")
             db.insert(name,null,values)
             db.close()
             return "success"
         }
         catch (e:Exception){
             return e.toString()
         }
     }
}