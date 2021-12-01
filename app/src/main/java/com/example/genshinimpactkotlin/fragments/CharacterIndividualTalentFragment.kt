package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.dto.Talents
import com.example.genshinimpactkotlin.dto.TalentsImages

class CharacterIndividualTalentFragment : Fragment() {
    private var talentsList: Talents = Talents()
    private var talentsImagesList: TalentsImages = TalentsImages()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        talentsList = arguments?.get("talents") as Talents
        talentsImagesList = arguments?.get("talentsImages") as TalentsImages

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_character_individual_talents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = parentFragmentManager.beginTransaction()
        createCombatTalents(transaction)
        transaction.commit()


    }

    private fun createCombatTalents(transaction: FragmentTransaction) {
        val talentContainer = R.id.ind_talentContainer
        transaction.add(talentContainer, ItemTalentFragment.newInstance(
            talentImage = talentsImagesList.combat1!!,
            talentName = talentsList.combat1?.name!!,
            talentInfo = talentsList.combat1?.info!!
        ))

        transaction.add(talentContainer, ItemTalentFragment.newInstance(
            talentImage = talentsImagesList.combat2!!,
            talentName = talentsList.combat2?.name!!,
            talentInfo = talentsList.combat2?.info!!
        ))

        transaction.add(talentContainer, ItemTalentFragment.newInstance(
            talentImage = talentsImagesList.combat3!!,
            talentName = talentsList.combat3?.name!!,
            talentInfo = talentsList.combat3?.info!!
        ))
    }
}