package com.example.genshinimpactkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    val mDatabase:FirebaseDatabase = FirebaseDatabase.getInstance()
    val dbSource:TaskCompletionSource<DataSnapshot> = TaskCompletionSource()
    val task = dbSource.task
    val characters:ArrayList<CharacterImageNameList> = arrayListOf()
    val allCharacters:ArrayList<String> = arrayListOf()
    val namesCharacters:ArrayList<String> = arrayListOf()
    val iconsCharacters:ArrayList<String> = arrayListOf()
    var idioma = "Spanish"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mToolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(mToolbar)
//        idioma = String

        startQuerys(
            mDatabase.getReference("Data/$idioma/characters"),
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
                dbSource.setResult(snapshot)
                queryCharacters(snapshot)
                queryNamesCharacters(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        refImagesCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryIconCharacters(snapshot)
                fillCharacters()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun fillCharacters() {
        for (num in 0 until allCharacters.count()) {
            characters.add(
                CharacterImageNameList(namesCharacters[num], iconsCharacters[num])
            )
        }
        initRecycler()
    }

    @SuppressLint("CutPasteId")
    fun initRecycler() {

        findViewById<RecyclerView>(R.id.rvCharacters).layoutManager = GridLayoutManager(applicationContext,3)
//        findViewById<RecyclerView>(R.id.rvCharacters).layoutManager = LinearLayoutManager(this)
        val adapter = CharacterAdapter(characters.toMutableList())
        findViewById<RecyclerView>(R.id.rvCharacters).adapter = adapter
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
    private fun queryIconCharacters(snapshot: DataSnapshot) {
        for (num in 0 until allCharacters.count()) {
            iconsCharacters.add(
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