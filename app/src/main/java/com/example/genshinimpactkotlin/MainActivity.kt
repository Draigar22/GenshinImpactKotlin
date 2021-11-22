package com.example.genshinimpactkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mDatabase: DatabaseReference
        val bt = findViewById<Button>(R.id.button1)
        val activity = Intent(this, Prueba::class.java)
        bt.setOnClickListener { v: View? -> startActivity(activity) }
    }
}