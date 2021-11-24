package com.example.genshinimpactkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import okhttp3.internal.cache.DiskLruCache

class MainActivity : AppCompatActivity() {
    val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
//    val characters:ArrayList<CharacterImageNameList> = arrayListOf()
    val characters:ArrayList<CharacterImageNameList> = arrayListOf(CharacterImageNameList("hola", "hola"))
    var idioma = "Spanish"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDatabase.keepSynced(true)
        setContentView(R.layout.activity_main)
        val mToolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(mToolbar)
//        val bt = findViewById<Button>(R.id.button1)
//        val activity = Intent(this, Prueba::class.java)
//        bt.setOnClickListener { startActivity(activity) }

        fillCharacter()


    }

    @SuppressLint("CutPasteId")
    fun initRecycler() {
        findViewById<RecyclerView>(R.id.rvCharacters).layoutManager = LinearLayoutManager(this)
        val adapter = CharacterAdapter(characters.toMutableList())
        findViewById<RecyclerView>(R.id.rvCharacters).adapter = adapter
    }

    /**
     *
     * Se encarga de llamar a los métodos necesarios para rellenar @param="characters:ArrayList"
     * con todos los nombres de los personajes e iconos correspondiente
     *
     */
    private fun fillCharacter() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val allCharacters:ArrayList<String> = queryCharacters(snapshot)
//                val namesCharacters:ArrayList<String> = queryNamesCharacters(snapshot, allCharacters)
//                val iconCharacter:ArrayList<String> = questAvatarCharacters(snapshot, allCharacters)
//                for (num in 0 until allCharacters.count()) {
//                    characters.add(
//                        CharacterImageNameList(namesCharacters[num], iconCharacter[num])
//
//                    )
//                    println(allCharacters)
//                }
                initRecycler()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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
    private fun queryCharacters(snapshot: DataSnapshot): ArrayList<String> {
        val allcharacters:ArrayList<String> = arrayListOf()
        snapshot.child("Image").child("characters").children.forEach{
            allcharacters.add(it.key.toString())

        }
        return allcharacters
    }

    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todas las direcciones de los avatares
     */
    private fun questAvatarCharacters(snapshot: DataSnapshot, allCharacters: ArrayList<String>): ArrayList<String> {
        val avatarCharacters:ArrayList<String> = arrayListOf()
        val snapshotaux:DataSnapshot = snapshot.child("Image").child("characters")
        for (num in 0 until allCharacters.count()) {
            avatarCharacters.add(
                snapshotaux.child(allCharacters[num])
                    .child("icon").value.toString())
        }
        return avatarCharacters
    }

    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todos los nombres de los personajes
     */
    private fun queryNamesCharacters(snapshot: DataSnapshot, allCharacters: ArrayList<String>): ArrayList<String> {
        val namesCharacters:ArrayList<String> = arrayListOf()
        val snapshotaux:DataSnapshot = snapshot.child("Data").child(idioma).child("characters")
        for (num in 0 until allCharacters.count()) {
            namesCharacters.add(
                snapshotaux.child(allCharacters[num])
                    .child("name").value.toString())
        }
        return namesCharacters
    }


}