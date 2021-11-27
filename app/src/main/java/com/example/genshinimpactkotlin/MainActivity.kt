package com.example.genshinimpactkotlin


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.genshinimpactkotlin.databinding.ActivityMainBinding
import com.example.genshinimpactkotlin.fragments.*

class MainActivity : AppCompatActivity() {

    private var charactersFragment = CharactersFragment()
    private var dashboardFragment = DashboardFragment()
    private var actualId = -10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.ic_dashboard
        replaceFragment(DashboardFragment())
        val view: View = findViewById(R.id.ic_dashboard)
        actualId = view.id


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_characters ->  {
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
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
            transaction.commit()

    }


}

