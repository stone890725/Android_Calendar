package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Schedule_Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule__detail)

        title="詳細內容"

        var id =intent.getStringExtra("ID")

        var tv_title = findViewById<TextView>(R.id.tv_title)
        var tv_time = findViewById<TextView>(R.id.tv_time)
        var tv_DetailContent = findViewById<TextView>(R.id.tv_DetailContent)

        var btn_cancel = findViewById<Button>(R.id.btn_cancel)
        var btn_finish =findViewById<Button>(R.id.btn_finish)



        var db = SqliteFunc(this)
        var sql = "SELECT Title,Content,Time FROM `Schedule` WHERE id='"+id+"'";
        var cursor = db.Select(sql)
        cursor.moveToFirst()


        tv_title.text=cursor.getString(0)
        tv_DetailContent.text=cursor.getString(1)
        tv_time.text="時間:"+cursor.getString(2)

        btn_cancel.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        btn_finish.setOnClickListener{
            var update = "UPDATE `Schedule` SET IsFinish='1' WHERE id='${id}'"
            db.execUd(update)
            AlertDialog.Builder(this)
                .setMessage("已完成該事項")
                .setTitle("警告")
                .setPositiveButton("OK"){
                    // 此為 Lambda 寫法
                        dialog, which ->  startActivity(Intent(this, MainActivity::class.java))
                }
                .show()

        }



    }
}

