package com.example.genshinimpactkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.interfaces.FirebaseCallBack
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.adapters.WeaponAdapter
import com.example.genshinimpactkotlin.clases.IndividualWeaponActivity
import com.example.genshinimpactkotlin.clases.WeaponImageNameList
import com.example.genshinimpactkotlin.dto.*
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

class WeaponsFragment : Fragment() {
    private val weapons: ArrayList<WeaponImageNameList> = arrayListOf()
    private var rvWeapons: RecyclerView? = null
    private var weaponListLocal: HashMap<String, Weapon> = hashMapOf()
    private var weaponImagesListLocal: HashMap<String, WeaponImage> = hashMapOf()
    private var language = "Spanish" // TODO IMPLEMENTAR FUNCIONALIDAD


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

        /* Cuando "weaponImagesListLocal" y "weaponListLocal" no estén vacías significará que
         ambas querys han sido realizadas, con lo cual se puede proceder a ordenar "weaponListLocal"
         e iniciar "fillWeapons" que necesitará datos de ambas colecciones. */
        readData(object: FirebaseCallBack {
            override fun onCallback() {
                if (weaponImagesListLocal.isNotEmpty() && weaponListLocal.isNotEmpty()) {
                    val weaponListSorted: MutableMap<String, Weapon> = TreeMap(weaponListLocal)
                    fillWeapons(weaponListSorted)
                }
            }
        }, mDatabase.getReference("Data/$language/weapons"), mDatabase.getReference("Image/weapons"))

    }

    /**
     * @param weaponListSorted MutableMap ordenado por <String>
     * Llena "weapons" e inicia el método initRecycler
     */
    private fun fillWeapons(weaponListSorted: MutableMap<String, Weapon>) {
        weaponListSorted.keys.forEach {
            weapons.add(
                WeaponImageNameList(
                    it,
                    weaponListLocal[it]?.name,
                    weaponListLocal[it]?.weapontype,
                    weaponListLocal[it]?.rarity,
                    weaponImagesListLocal[it]?.icon
                )
            )
        }
        initRecycler()
    }

    /**
     * Llena rvWeapons (RecyclerView) con su correspondiente adaptador y modificaciones
     */
    private fun initRecycler() {
        val adapter = WeaponAdapter(weapons)
        adapter.setOnItemClickListener(object : WeaponAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualWeaponActivity::class.java).apply {
                    putExtra("weapon", weaponListLocal.getValue(defaultName))
                    putExtra("weaponImage", weaponImagesListLocal.getValue(defaultName))
                }
                startActivity(intent)
            }
        })

        rvWeapons?.adapter = adapter
        val columns = (((resources.displayMetrics.widthPixels))/200)-1;
        rvWeapons?.layoutManager = GridLayoutManager(context, columns)
    }

    /**
     * Este método se utiliza de forma auxiliar para utilizar la interface "FirebaseCallBack"
     * que se llama al final de cada query.
     */
    private fun readData(firebaseCallBack: FirebaseCallBack, refWeapons: DatabaseReference, refWeaponImage: DatabaseReference) {
        refWeapons.keepSynced(true)
        refWeapons.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    weaponListLocal[it.key.toString()] =
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
                firebaseCallBack.onCallback()
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })
        refWeapons.keepSynced(true)
        refWeaponImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    weaponImagesListLocal[it.key.toString()] =
                        WeaponImage(
                            it.child("icon").value.toString(),
                            it.child("awakenicon").value.toString()
                        )

                }
                firebaseCallBack.onCallback()

            }
            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })

    }
}
