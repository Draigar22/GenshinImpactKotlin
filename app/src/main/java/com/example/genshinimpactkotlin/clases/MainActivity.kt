package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.fragments.*
import com.google.firebase.database.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    // TODO EN PORTRAIT (giro pantalla) DE CHARACTERS HABRÏA QUE QUITAR 1 PERONAJE PARA QUE QUERE MEJOR
    // TODO ANIMACIONES BOTONES
    // TODO EL VIAJERO EN LISTA PERSONAJES ESTÁ BUG, (Electro, geo, anemo...)

    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var language: String = "English"
    private var actualId: Int = -10
    private var firstTime: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bn: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val bundle = Bundle()
        bundle.putString("language", language)
        if (savedInstanceState == null) {
            mDatabase.setPersistenceEnabled(true)
            val fragment = CharactersFragment()
            fragment.arguments = bundle
            replaceFragment(fragment)
            bn.selectedItemId = R.id.ic_characters
            actualId = R.id.ic_characters
        }

        bn.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_characters -> {
                    if (actualId != R.id.ic_characters) {
                        val fragment = CharactersFragment()
                        fragment.arguments = bundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_characters
                    }
                }
                R.id.ic_weapons -> {
                    if (actualId != R.id.ic_weapons) {
                        val fragment = WeaponsFragment()
                        fragment.arguments = bundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_weapons
                    }
                }
                R.id.ic_artifacts -> {
                    if (actualId != R.id.ic_artifacts) {
                        val fragment = ArtifactsFragment()
                        fragment.arguments = bundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_artifacts
                    }
                }
                R.id.ic_map -> {
                    if (actualId != R.id.ic_map) {
                        val fragment = MapFragment()
                        val bundle = Bundle()
                        bundle.putBoolean("alertDialog", firstTime)
                        fragment.arguments = bundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_map
                        if (!firstTime) {
                            firstTime = true
                        }
                    }
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }
    override fun onBackPressed() {
        val bn:BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (bn.selectedItemId == R.id.ic_characters) {
            super.onBackPressed()
            finish()
        } else {
            bn.selectedItemId = R.id.ic_characters
        }
    }



}
