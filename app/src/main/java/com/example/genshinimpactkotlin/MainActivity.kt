package com.example.genshinimpactkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    var idioma = "Spanish"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val mToolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(mToolbar)

        val bt = findViewById<Button>(R.id.button1)
        val activity = Intent(this, Prueba::class.java)

        val characters:ArrayList<CharacterImageNameList> = fillCharacter()
        println("HOLAAAAAAAAAAAAAAAAAAAA" + characters.count())
        characters.forEach{
            println(it)
        }

        bt.setOnClickListener { startActivity(activity) }

    }

    /**
     *
     * Se encarga de llamar a los métodos necesarios para devolver un ArrayList<CharacterImageNameList>
     * Relleno con todos los nombres de los personajes y avatares correspondiente
     *
     */
    private fun fillCharacter(): ArrayList<CharacterImageNameList> {
        val characters:ArrayList<CharacterImageNameList> = arrayListOf()
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allCharacters:ArrayList<String> = queryCharacters(snapshot)
                val namesCharacters:ArrayList<String> = queryNamesCharacters(snapshot, allCharacters)
                val avatarCharacters:ArrayList<String> = questAvatarCharacters(snapshot, allCharacters)
                for (num in 0 until allCharacters.count()) {
                    characters.add(
                        CharacterImageNameList(namesCharacters[num], avatarCharacters[num])
                    )
                }
//                    Picasso.get().load(snapshot.value.toString()).into(iv)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return characters
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
        snapshot.child("Image").child("characters").children.forEachIndexed { index, dataSnapshot ->
            avatarCharacters.add(dataSnapshot.child(allCharacters[index]).child("icon").getValue().toString())

        }
        avatarCharacters.forEach{
            println(it)
        }
        return avatarCharacters
    }

    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todos los nombres de los personajes
     */
    private fun queryNamesCharacters(snapshot: DataSnapshot, allCharacters: ArrayList<String>): ArrayList<String> {
        val namesCharacters:ArrayList<String> = arrayListOf()
        snapshot.child("Data").child(idioma).child("characters").children.forEachIndexed { index, dataSnapshot ->
            namesCharacters.add(dataSnapshot.child(allCharacters[index]).child("name").getValue().toString())
        }
        return namesCharacters
    }


}