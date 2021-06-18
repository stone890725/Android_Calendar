package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Finish_Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish__detail)

        title="已完成詳細內容"

        var id =intent.getStringExtra("ID")

        var tv_title = findViewById<TextView>(R.id.tv_title_F)

        var tv_DetailContent = findViewById<TextView>(R.id.tv_Content_F)

        var btn_cancel = findViewById<Button>(R.id.button_return)

        var db = SqliteFunc(this)
        var sql = "SELECT Title,Content FROM `Schedule` WHERE id='"+id+"'";
        var cursor = db.Select(sql)
        cursor.moveToFirst()


        tv_title.text=cursor.getString(0)
        tv_DetailContent.text=cursor.getString(1)

        btn_cancel.setOnClickListener{
            startActivity(Intent(this, IsFinishActivity::class.java))
        }


    }
}