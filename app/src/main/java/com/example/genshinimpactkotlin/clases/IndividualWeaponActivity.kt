package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.entidades.*
import com.example.genshinimpactkotlin.fragments.WeaponIndividualInfoFragment


class IndividualWeaponActivity : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_template)
        val transaction = supportFragmentManager.beginTransaction()

        // Fragmento WeaponIndividualInfoFragment
        val fragmentStat = WeaponIndividualInfoFragment()
        val bundle = Bundle()
        bundle.putParcelable("weapon", intent.extras?.get("weapon") as Weapon)
        bundle.putParcelable("weaponImage", intent.extras?.get("weaponImage") as WeaponImage)
        fragmentStat.arguments = bundle
        transaction.replace(R.id.ind_containerFragment, fragmentStat)
        transaction.commit()
    }


    }





