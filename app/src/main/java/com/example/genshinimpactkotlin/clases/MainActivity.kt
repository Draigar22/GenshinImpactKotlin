package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.databinding.ActivityMainBinding
import com.example.genshinimpactkotlin.fragments.*
import com.google.firebase.database.*
import com.example.genshinimpactkotlin.dto.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    // TODO EN PORTRAIT (giro pantalla) DE CHARACTERS HABRÏA QUE QUITAR 1 PERONAJE PARA QUE QUERE MEJOR
    // TODO ANIMACIONES BOTONES

    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var language: String = "Spanish"
    private var actualId: Int = -10
    private val characterList: HashMap<String, Character>  = hashMapOf()
    private val characterListImage: HashMap<String, CharacterImage> = hashMapOf()
    private val elementListImage: HashMap<String, ElementImage> = hashMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bn:BottomNavigationView = findViewById(R.id.bottom_navigation)
        bn.selectedItemId = R.id.ic_dashboard
        actualId = R.id.ic_dashboard
        startQuerys(
            mDatabase.getReference("Data/$language/characters"),
            mDatabase.getReference("Image")
        )



        bn.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_characters -> {
                    if (actualId != R.id.ic_characters) {
                        val fragment = CharactersFragment()
                        val bundle = Bundle()
                        bundle.putSerializable("characterList", characterList)
                        bundle.putSerializable("characterImageList", characterListImage)
                        bundle.putSerializable("elementImageList", elementListImage)

                        fragment.arguments = bundle
                        replaceFragment(fragment)
                        actualId = R.id.ic_characters
                    }
                }
                R.id.ic_dashboard -> {
                    if (actualId != R.id.ic_dashboard) {
                        replaceFragment(DashboardFragment())
                        actualId = R.id.ic_dashboard
                    }
                }
                R.id.ic_settings -> {
                        replaceFragment(BlankFragment())
                }
            }
            true
        }

    }

    override fun onBackPressed() {
        val bn:BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (bn.selectedItemId == R.id.ic_dashboard) {
            super.onBackPressed()
            finish()
        } else {
            bn.selectedItemId = R.id.ic_dashboard
        }
    }
    private fun startQuerys(refCharacters: DatabaseReference, refImage: DatabaseReference) {
        // Consulta a refCharacters
        refCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    characterList.put(it.key.toString(), it.getValue(Character::class.java)!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })


            // Consulta a refImage
        refImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.child("elements").children.forEach {
                    elementListImage.put(it.key.toString(), it.getValue(ElementImage::class.java)!!)
                }
                snapshot.child("characters").children.forEach {
                    characterListImage.put(it.key.toString(), it.getValue(CharacterImage::class.java)!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })


    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }

}

