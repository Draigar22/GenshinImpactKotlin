package com.example.genshinimpactkotlin.clases


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.databinding.ActivityMainBinding
import com.example.genshinimpactkotlin.fragments.*
import com.google.firebase.database.*
import com.example.genshinimpactkotlin.dto.*

class MainActivity : AppCompatActivity() {

    // TODO EN PORTRAIT (giro pantalla) DE CHARACTERS HABRÏA QUE QUITAR 1 PERONAJE PARA QUE QUERE MEJOR
    private var actualId = -10
    private val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var language: String = "Spanish"
    private val characterList: ArrayList<Character> = arrayListOf()
    private val imageList: ArrayList<ImageCharacter> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.ic_dashboard


        startQuerys(
            mDatabase.getReference("Data/$language/characters"),
            mDatabase.getReference("Image/characters")
        )


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_characters -> {
                    if (actualId != R.id.ic_characters) {
                        actualId = R.id.ic_characters
                        val fragment = CharactersFragment()
                        val bundle = Bundle().apply {
                            putParcelableArrayList("characterList", characterList)
                            putParcelableArrayList("imageList", imageList)
                        }
                        fragment.arguments = bundle
                        replaceFragment(fragment)
                    }
                }
                R.id.ic_dashboard -> {
                    if (actualId != R.id.ic_dashboard) {
                        actualId = R.id.ic_dashboard

                        replaceFragment(DashboardFragment())
                    }
                }
                R.id.ic_settings -> {
                    if (actualId != R.id.ic_settings) {
                        actualId = R.id.ic_settings
                        replaceFragment(BlankFragment())
                    }
                }
            }
            true
        }

    }

    private fun startQuerys(refCharacters: DatabaseReference, refImage: DatabaseReference) {
        // Consulta a refCharacters
        refCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEachIndexed { i, data ->
                    run {
                        characterList.add(data.getValue(Character::class.java)!!)
                        characterList[i].defaultName = data.key.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })


            // Consulta a refImage
        refImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    imageList.add(it.getValue(ImageCharacter::class.java)!!)
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
//        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

}

