package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.dto.*
import com.example.genshinimpactkotlin.interfaces.FirebaseCallBack
import com.google.firebase.database.*

class CharacterIndividualTalentFragment : Fragment() {
    private var talentsList: Talents = Talents()
    private var talentsImagesList: TalentsImages = TalentsImages()
    private var language: String = "Spanish"
    private var defaultName: String = ""

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language = arguments!!.getString("language").toString()
        defaultName = arguments!!.getString("defaultName").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_character_individual_talents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = parentFragmentManager.beginTransaction()
        val mDatabase = FirebaseDatabase.getInstance()
        readData(object : FirebaseCallBack {
            override fun onCallback() {
                if (talentsList.combat1 != null && talentsImagesList.combat1 != null) {
                    createCombatTalents(transaction)
                    transaction.commit()
                }
            }

        },
            mDatabase.getReference("Data/$language/talents/$defaultName"),
            mDatabase.getReference("Image/talents/$defaultName")
        )




    }

    private fun readData(firebaseCallBack: FirebaseCallBack, refTalents: DatabaseReference, refTalentsImage: DatabaseReference) {
        refTalents.keepSynced(true)
        refTalents.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                talentsList = snapshot.getValue(Talents::class.java)!!
                firebaseCallBack.onCallback()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        refTalentsImage.keepSynced(true)
        refTalentsImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                talentsImagesList = snapshot.getValue(TalentsImages::class.java)!!
                firebaseCallBack.onCallback()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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