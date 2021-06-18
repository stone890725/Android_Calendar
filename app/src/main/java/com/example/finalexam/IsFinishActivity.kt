package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import java.util.ArrayList

class IsFinishActivity : AppCompatActivity() {
    private var items : ArrayList<String> = ArrayList()  //定義資料清單
    private var id : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_finish)
        title = "已完成事項"
        val listView = findViewById<ListView>(R.id.IsFinish_listView)
        val db =SqliteFunc(this)

        var sql = "SELECT Title,id FROM `Schedule` WHERE  IsFinish='"+1+"'ORDER BY Time ASC"
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
            listView.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, index, l ->
                //Toast.makeText(this@MainActivity, "我是item點擊事件 i = " + i + "l = " + l, Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Finish_Detail::class.java)
                System.out.println("id[index]=" + id[index])

                intent.putExtra("ID", id[index])
                startActivity(intent)
            })
        }


    }
}