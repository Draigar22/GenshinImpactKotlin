package com.example.genshinimpactkotlin.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.CharacterAdapter
import com.example.genshinimpactkotlin.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharactersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharactersFragment : Fragment() {
    var rvCharacters:RecyclerView? = null
    val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val characters: ArrayList<CharacterImageNameList> = arrayListOf()
    var adapter = CharacterAdapter(characters.toMutableList())
    val allCharacters: ArrayList<String> = arrayListOf()
    val namesCharacters: ArrayList<String> = arrayListOf()
    val avatarCharacters: ArrayList<String> = arrayListOf()
    val elementCharacters: ArrayList<String> =
        arrayListOf() // TODO // HACER ARRAY ELEMENTOS Y TIPOARMA O ADAPTAR
    var language = "Spanish"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        language = String TODO // METER FUNCIONALIDAD PARA CAMBIAR IDIOMA

        startQuerys(
            mDatabase.getReference("Data/$language/characters"),
            mDatabase.getReference("Image/characters")
        )
    }

    override fun onStop() {
        super.onStop()
        rvCharacters == null
        allCharacters.clear()
        avatarCharacters.clear()
        namesCharacters.clear()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_characters, container, false);
        rvCharacters = view.findViewById(R.id.rvCharacters)
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



    @SuppressLint("CutPasteId")
    fun initRecycler() {
//        findViewById<RecyclerView>(R.id.rvCharacters).layoutManager = LinearLayoutManager(this)
        adapter = CharacterAdapter(characters.toMutableList())
        rvCharacters?.adapter = adapter
        rvCharacters?.layoutManager = GridLayoutManager(rvCharacters?.context, 3)
        adapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String) {
                Toast.makeText(rvCharacters?.context, "Notificación corta", Toast.LENGTH_SHORT).show()
            }
        })
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
        return
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
}
