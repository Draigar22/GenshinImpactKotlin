package com.example.genshinimpactkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IndividualCharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)
        var nombre: String? = intent.getStringExtra("defaultName")
        Toast.makeText(applicationContext, nombre, Toast.LENGTH_LONG).show()

    }
}