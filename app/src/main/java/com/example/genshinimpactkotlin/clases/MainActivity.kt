package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.fragments.SettingsFragment
import com.example.genshinimpactkotlin.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class MainActivity : AppCompatActivity() {

    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var actualId: Int = -10

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val bn: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val languageBundle = Bundle()

        // Primera vez que se crea el main activity
        if (savedInstanceState == null) {
            languageBundle.putString("language", languageSettings())
            darkMode()
            mDatabase.setPersistenceEnabled(true)
            val fragment = CharactersFragment()
            fragment.arguments = languageBundle
            replaceFragment(fragment)
            bn.selectedItemId = R.id.ic_characters
            actualId = R.id.ic_characters
        }

        bn.setOnItemSelectedListener {
            languageBundle.putString("language", languageSettings())
            when (it.itemId) {
                R.id.ic_characters -> {
                    if (actualId != R.id.ic_characters) {
                        val fragment = CharactersFragment()
                        fragment.arguments = languageBundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_characters
                    }
                }
                R.id.ic_weapons -> {
                    if (actualId != R.id.ic_weapons) {
                        val fragment = WeaponsFragment()
                        fragment.arguments = languageBundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_weapons
                    }
                }
                R.id.ic_artifacts -> {
                    if (actualId != R.id.ic_artifacts) {
                        val fragment = ArtifactsFragment()
                        fragment.arguments = languageBundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_artifacts
                    }
                }
                R.id.ic_map -> {
                    if (actualId != R.id.ic_map) {
                        replaceFragment(MapFragment())
                        actualId = R.id.ic_map
                    }
                }
                R.id.ic_settings -> {
                    if (actualId != R.id.ic_settings) {
                        replaceFragment(SettingsFragment())
                        actualId = R.id.ic_settings
                    }
                }
            }
            true
        }

    }

    /**
     * @param fragment es reemplazado por el anterior
     */
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    /**
     * Cuando se va hacia atrás automáticamente te lleva al apartado "Characters", si estás en el
     * se cierra la App
     */
    override fun onBackPressed() {
        val bn:BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (bn.selectedItemId == R.id.ic_characters) {
            super.onBackPressed()
            finish()
        } else {
            bn.selectedItemId = R.id.ic_characters
        }
    }

    /**
     * Consulta el idioma configurado en "Opciones" y devuelve el valor.
     */
    private fun languageSettings(): String {
        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val language = sp.getString("languageAppContent", "").toString()
        if (language.isBlank())
            return "Spanish"
        else
            return language

    }

    /**
     * Consulta el tema configurado (Oscudo o Blanco), por predeterminado y la primera vez
     * será oscuro.
     */
    private fun darkMode() {
        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val darkMode = sp.getBoolean("theme", true)
        if (darkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }


}
