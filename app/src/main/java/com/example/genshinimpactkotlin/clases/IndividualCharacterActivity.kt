package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.dto.*
import com.example.genshinimpactkotlin.fragments.CharacterIndividualInfoFragment
import com.example.genshinimpactkotlin.fragments.CharacterIndividualTalentFragment


class IndividualCharacterActivity : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_template)
        val transaction = supportFragmentManager.beginTransaction()

        // Primer fragmento
        val fragmentStat = CharacterIndividualInfoFragment()
        var bundle = Bundle()
        bundle.putParcelable("character", intent.extras?.get("character") as Character)
        bundle.putParcelable("characterImage", intent.extras?.get("characterImage") as CharacterImage)
        bundle.putSerializable("elementImageList", intent.extras?.get("elementImageList") as HashMap<String, ElementImage>)
        fragmentStat.arguments = bundle
        transaction.replace(R.id.ind_containerFragment, fragmentStat)

        // Segundo fragmento
        val fragmentTalent = CharacterIndividualTalentFragment()
        bundle = Bundle()
        bundle.putParcelable("talents", intent.extras?.get("talents") as Talents)
        bundle.putParcelable("talentsImages",
            intent.extras?.get("talentsImages") as TalentsImages
        )
        fragmentTalent.arguments = bundle
        transaction.add(R.id.ind_containerFragment, fragmentTalent)

        transaction.commit()
    }


    }





