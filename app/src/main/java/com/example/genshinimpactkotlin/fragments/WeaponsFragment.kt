package com.example.genshinimpactkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.adapters.CharacterAdapter
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.adapters.WeaponAdapter
import com.example.genshinimpactkotlin.clases.IndividualCharacterActivity
import com.example.genshinimpactkotlin.clases.IndividualWeaponActivity
import com.example.genshinimpactkotlin.clases.WeaponImageNameList
import com.example.genshinimpactkotlin.dto.*
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class WeaponsFragment : Fragment() {
    private val weapons: ArrayList<WeaponImageNameList> = arrayListOf()
    private var rvWeapons: RecyclerView? = null
    val weaponList: HashMap<String, Weapon> = hashMapOf()
    val weaponImagesList: HashMap<String, WeaponImage> = hashMapOf()

    var language = "Spanish" // TODO IMPLEMENTAR FUNCIONALIDAD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        language = arguments?.getString("language").toString()

        return inflater.inflate(R.layout.fragment_weapons, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        rvWeapons = view.findViewById(R.id.rvWeapons)
        startQuerys(
            mDatabase.getReference("Data/$language/weapons"),
            mDatabase.getReference("Image/weapons")
        )
    }

    private fun startQuerys(refWeapons: DatabaseReference, refWeaponImage: DatabaseReference) {
        refWeapons.keepSynced(true)
        refWeaponImage.keepSynced(true)

        refWeaponImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    weaponImagesList[it.key.toString()] =
                        WeaponImage(
                            it.child("icon").value.toString(),
                            it.child("awakenicon").value.toString()
                        )

                }

            }
            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })
        refWeapons.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    weaponList[it.key.toString()] =
                        Weapon(
                            it.child("name").value.toString(),
                            it.child("weapontype").value.toString(),
                            it.child("rarity").value.toString(),
                            it.child("baseatk").value.toString().toInt(),
                            it.child("substat").value.toString(),
                            it.child("description").value.toString(),
                            it.child("effectname").value.toString(),
                            it.child("effect").value.toString(),
                            when(it.child("r1").exists()) {
                                true -> it.child("r1").value as List<String>
                                false -> null
                            }
                        )
                }
                val weaponListSorted: MutableMap<String, Weapon> = TreeMap(weaponList)
                fillWeapons(weaponListSorted)
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })
    }


    private fun fillWeapons(weaponListSorted: MutableMap<String, Weapon>) {
        weaponListSorted.keys.forEach {
            weapons.add(
                WeaponImageNameList(
                    it,
                    weaponList[it]?.name,
                    weaponList[it]?.weapontype,
                    weaponList[it]?.rarity,
                    weaponImagesList[it]?.icon
                )
            )
        }
        initRecycler()
    }

    private fun initRecycler() {
        val adapter = WeaponAdapter(weapons)
        adapter.setOnItemClickListener(object : WeaponAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualWeaponActivity::class.java).apply {
                    putExtra("weapon", weaponList.getValue(defaultName))
                    putExtra("weaponImage", weaponImagesList.getValue(defaultName))
                }
                startActivity(intent)
            }
        })

        rvWeapons?.adapter = adapter
        val columns = (((resources.displayMetrics.widthPixels))/200)-1;
        rvWeapons?.layoutManager = GridLayoutManager(context, columns)
    }


}
