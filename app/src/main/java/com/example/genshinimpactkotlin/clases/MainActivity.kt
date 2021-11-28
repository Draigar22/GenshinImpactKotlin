package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.databinding.ActivityMainBinding
import com.example.genshinimpactkotlin.fragments.*

class MainActivity : AppCompatActivity() {

    // TODO EN PORTRAIT (giro pantalla) DE CHARACTERS HABRÏA QUE QUITAR 1 PERONAJE PARA QUE QUERE MEJOR
    private var actualId = -10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.ic_dashboard

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_characters -> {
                    if (actualId != R.id.ic_characters) {
                        actualId = R.id.ic_characters
                        replaceFragment(CharactersFragment())
                    }
                }
                R.id.ic_dashboard -> {
                    if (actualId != R.id.ic_dashboard) {
                        actualId = R.id.ic_dashboard
                        replaceFragment(DashboardFragment())
                    }
                }
                R.id.ic_settings -> {
                    if (actualId != R.id.ic_settings) {
                        actualId = R.id.ic_settings
                        replaceFragment(DashboardFragment())
                    }
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
//        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
//        transaction.addToBackStack(null)
        transaction.commit()
    }


}

