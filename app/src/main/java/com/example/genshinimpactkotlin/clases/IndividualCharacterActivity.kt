package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import android.view.SurfaceControl
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.fragments.IndividualCharacterFragmentStat


class IndividualCharacterActivity : AppCompatActivity() {

    // TODO EN PORTRAIT (giro pantalla) DE CHARACTERS HABRÏA QUE QUITAR 1 PERONAJE PARA QUE QUERE MEJOR
    private var actualId = -10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_character)
        val fragment = IndividualCharacterFragmentStat()
        if (fragment.arguments == null)
            fragment.arguments = intent.extras
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ind_containerFragment, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }


    }





