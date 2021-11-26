package com.example.genshinimpactkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CharactersMainActivity : AppCompatActivity() {

    val mDatabase:FirebaseDatabase = FirebaseDatabase.getInstance()

    val characters:ArrayList<CharacterImageNameList> = arrayListOf()
    val allCharacters:ArrayList<String> = arrayListOf()
    val namesCharacters:ArrayList<String> = arrayListOf()
    val avatarCharacters:ArrayList<String> = arrayListOf()
    val elementCharacters:ArrayList<String> = arrayListOf() // TODO // HACER ARRAY ELEMENTOS Y TIPOARMA O ADAPTAR
    var language = "Spanish"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mToolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(mToolbar)
//        idioma = String TODO // METER FUNCIONALIDAD PARA CAMBIAR IDIOMA

        startQuerys(
            mDatabase.getReference("Data/$language/characters"),
            mDatabase.getReference("Image/characters")
        )
    }

    /**
     *
     * Se encarga de llamar a los métodos necesarios para rellenar @param="characters:ArrayList"
     * con todos los nombres de los personajes e iconos correspondiente
     *
     */
    private fun startQuerys(refCharacters:DatabaseReference, refImagesCharacters:DatabaseReference) {
        refCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryCharacters(snapshot)
                queryNamesCharacters(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        refImagesCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryAvatarCharacters(snapshot)
                fillCharacters()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun fillCharacters() {
        for (num in 0 until allCharacters.count()) {
            characters.add(
                CharacterImageNameList(allCharacters[num], namesCharacters[num], avatarCharacters[num])
            )
        }
        initRecycler()
    }

    @SuppressLint("CutPasteId")
    fun initRecycler() {
        val mRecyclerView:RecyclerView = findViewById(R.id.rvCharacters)
//        findViewById<RecyclerView>(R.id.rvCharacters).layoutManager = LinearLayoutManager(this)
        val adapter = CharacterAdapter(characters.toMutableList())
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = GridLayoutManager(applicationContext,3)
        adapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener{
            override fun onItemClick(defaultName: String) {
                val intentIndividualCharacterActivity = Intent(this@CharactersMainActivity, IndividualCharacterActivity::class.java
                ).putExtra("defaultName", defaultName).putExtra("language", language)
                startActivity(intentIndividualCharacterActivity)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    /**
     * Recibe un snapshot para hacer la query correspondiente para devolver un arraylist de strings
     * con los nombres (estos serán como claves ya que tienen el mismo nombre en otras rutas)
     */
    private fun queryCharacters(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            allCharacters.add(it.key.toString())
        }
        return
    }

    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todas las direcciones de los avatares
     */
    private fun queryAvatarCharacters(snapshot: DataSnapshot) {
        for (num in 0 until allCharacters.count()) {
            avatarCharacters.add(
                snapshot.child(allCharacters[num])
                    .child("icon").value.toString())
        }
    }

    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todos los nombres de los personajes
     */
    private fun queryNamesCharacters(snapshot: DataSnapshot) {
        for (num in 0 until allCharacters.count()) {
            namesCharacters.add(
                snapshot.child(allCharacters[num])
                    .child("name").value.toString())

        }
        return
    }


}