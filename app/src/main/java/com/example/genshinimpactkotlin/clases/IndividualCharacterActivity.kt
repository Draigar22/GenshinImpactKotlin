package com.example.genshinimpactkotlin.clases

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.genshinimpactkotlin.R

class IndividualCharacterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)
        val nombre: String? = intent.getStringExtra("defaultName")
        Toast.makeText(applicationContext, nombre, Toast.LENGTH_LONG).show()

    }
}