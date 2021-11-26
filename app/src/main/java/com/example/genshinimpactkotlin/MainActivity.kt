package com.example.genshinimpactkotlin


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.genshinimpactkotlin.databinding.ActivityMainBinding
import com.example.genshinimpactkotlin.fragments.*

class MainActivity : AppCompatActivity() {

    private val charactersFragment = CharactersFragment()
    private val dashboardFragment = DashboardFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_characters -> replaceFragment(charactersFragment)
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }


}

