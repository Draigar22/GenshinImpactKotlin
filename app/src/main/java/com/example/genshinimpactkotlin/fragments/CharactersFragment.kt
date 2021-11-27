package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.adapters.CharacterAdapter
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.google.firebase.database.*

class CharactersFragment : Fragment() {
    val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val characters: ArrayList<CharacterImageNameList> = arrayListOf()
    val allCharacters: ArrayList<String> = arrayListOf()
    val namesCharacters: ArrayList<String> = arrayListOf()
    val avatarCharacters: ArrayList<String> = arrayListOf()
    var rvCharacters: RecyclerView? = null
    val elementCharacters: ArrayList<String> =
        arrayListOf() // TODO // HACER ARRAY ELEMENTOS Y TIPOARMA O ADAPTAR
    var language = "Spanish" // TODO IMPLEMENTAR FUNCIONALIDAD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_characters, container, false);
        rvCharacters = view.findViewById(R.id.rvCharacters)
        startQuerys(
            mDatabase.getReference("Data/$language/characters"),
            mDatabase.getReference("Image/characters")
        )
        return view
    }


    private fun fillCharacters() {
        for (num in 0 until allCharacters.count()) {
            characters.add(
                CharacterImageNameList(
                    allCharacters[num],
                    namesCharacters[num],
                    avatarCharacters[num]
                )
            )
        }
        initRecycler()

    }

    private fun initRecycler() {
        val adapter = CharacterAdapter(characters)
        adapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String) {
                Toast.makeText(rvCharacters?.context, defaultName, Toast.LENGTH_SHORT).show()
            }
        })
        rvCharacters?.adapter = adapter
        val columns = (((resources.displayMetrics.widthPixels  ))/200)-1;
        rvCharacters?.layoutManager = GridLayoutManager(context, columns)
    }

    /**
     * Recibe un snapshot para hacer la query correspondiente para devolver un arraylist de strings
     * con los nombres (estos serán como claves ya que tienen el mismo nombre en otras rutas)
     */
    private fun queryCharacters(snapshot: DataSnapshot) {
        snapshot.children.forEach {
            allCharacters.add(it.key.toString())
        }
    }



    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todas las direcciones de los avatares
     */
    private fun queryAvatarCharacters(snapshot: DataSnapshot) {
        for (num in 0 until allCharacters.count()) {
            avatarCharacters.add(
                snapshot.child(allCharacters[num])
                    .child("icon").value.toString()
            )
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
                    .child("name").value.toString()
            )

        }

    }

    /**
     *
     * Se encarga de llamar a los métodos necesarios para rellenar @param="characters:ArrayList"
     * con todos los nombres de los personajes e iconos correspondiente
     *
     */
    private fun startQuerys(
        refCharacters: DatabaseReference,
        refImagesCharacters: DatabaseReference
    ) {

        refCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryCharacters(snapshot)
                queryNamesCharacters(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })

        refImagesCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryAvatarCharacters(snapshot)
                fillCharacters()
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })

    }
}
