package com.example.genshinimpactkotlin.fragments

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.interfaces.FirebaseCallBack
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.adapters.WeaponAdapter
import com.example.genshinimpactkotlin.clases.IndividualWeaponActivity
import com.example.genshinimpactkotlin.entidades.WeaponImageName
import com.example.genshinimpactkotlin.entidades.*
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

class WeaponsFragment : Fragment(), SearchView.OnQueryTextListener {
    private val weapons: ArrayList<WeaponImageName> = arrayListOf()
    private var rvWeapons: RecyclerView? = null
    private var weaponList: HashMap<String, Weapon> = hashMapOf()
    private var weaponImagesList: HashMap<String, WeaponImage> = hashMapOf()
    private var language = "Spanish" // TODO IMPLEMENTAR FUNCIONALIDAD
    private var searchView: SearchView? = null
    private var weaponAdapter: WeaponAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        language = arguments?.getString("language").toString()
        return inflater.inflate(R.layout.fragment_weapons, container, false);
    }

    /**
     *  Cuando "weaponImagesListLocal" y "weaponListLocal" no estén vacías significará que
     *  ambas querys han sido realizadas, con lo cual se puede proceder a ordenar "weaponListLocal"
     *  e iniciar "fillWeapons" que necesitará datos de ambas colecciones.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        rvWeapons = view.findViewById(R.id.rvWeapons)
        searchView = view.findViewById(R.id.searchViewWeapon)
        initListener()

        readData(object: FirebaseCallBack {
            override fun onCallback() {
                if (weaponImagesList.isNotEmpty() && weaponList.isNotEmpty()) {
                    val weaponListSorted: MutableMap<String, Weapon> = TreeMap(weaponList)
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
                WeaponImageName(
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

    /**
     * Llena rvWeapons (RecyclerView) con su correspondiente adaptador y modificaciones
     */
    private fun initRecycler() {
        weaponAdapter = WeaponAdapter(weapons)
        weaponAdapter!!.setOnItemClickListener(object : WeaponAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualWeaponActivity::class.java).apply {
                    putExtra("weapon", weaponList.getValue(defaultName))
                    putExtra("weaponImage", weaponImagesList.getValue(defaultName))
                }
                startActivity(intent)
            }
        })

        rvWeapons?.adapter = weaponAdapter
        val columns = (((context?.resources?.displayMetrics?.widthPixels))?.div(270));
        rvWeapons?.layoutManager = columns?.let { GridLayoutManager(context, it) }
    }

    /**
     * Este método se utiliza de forma auxiliar para utilizar la interface "FirebaseCallBack"
     * que se llama al final de cada query.
     * Dentro se realizan 2 Querys independientes a la base de datos
     */
    private fun readData(firebaseCallBack: FirebaseCallBack, refWeapons: DatabaseReference, refWeaponImage: DatabaseReference) {
        refWeapons.keepSynced(true)
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
                    weaponImagesList[it.key.toString()] =
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
    private fun initListener() {
        searchView?.setOnQueryTextListener(this)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        weaponAdapter?.filterName(query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        weaponAdapter?.filterName(newText!!)
        return false
    }
}
