package com.example.genshinimpactkotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.adapters.CharacterAdapter
import com.example.genshinimpactkotlin.entidades.CharacterImageName
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.clases.IndividualCharacterActivity
import com.example.genshinimpactkotlin.entidades.*
import com.example.genshinimpactkotlin.interfaces.FirebaseCallBack
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

class CharactersFragment : Fragment(), SearchView.OnQueryTextListener {
    private val characters: ArrayList<CharacterImageName> = arrayListOf()
    private var rvCharacters: RecyclerView? = null
    private var characterList: HashMap<String, Character> = hashMapOf()
    private var characterImageList: HashMap<String, CharacterImage> = hashMapOf()
    private var elementImageList: HashMap<String, ElementImage> = hashMapOf()
    private var language = ""
    private var searchView : SearchView? = null
    private var characterAdapter: CharacterAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_characters, container, false)
        language = arguments?.getString("language").toString()
        return view
    }


    /**
     *  Cuando "characterImageList" y "characterList" no estén vacías significará que
     *  ambas querys han sido realizadas, con lo cual se puede proceder a ordenar "characterList"
     *  e iniciar "fillCharacters" que necesitará datos de ambas colecciones.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvCharacters = view.findViewById(R.id.rvCharacter)
        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        searchView = view.findViewById(R.id.searchViewCharacters)
        initListener()
        readData(object: FirebaseCallBack {
            override fun onCallback() {
                if (characterList.isNotEmpty() && characterImageList.isNotEmpty()) {
                    val characterListSorted: MutableMap<String, Character> = TreeMap(characterList)
                    fillCharacters(characterListSorted)
                }
            }

        }, mDatabase.getReference("Data/$language/characters"),
            mDatabase.getReference("Image"))
    }

    private fun initListener() {
        searchView?.setOnQueryTextListener(this)
    }

    /**
     * Este método se utiliza de forma auxiliar para utilizar la interface "FirebaseCallBack"
     * que se llama al final de cada query.
     * Dentro se realizan 2 Querys independientes a la base de datos
     */
    private fun readData(firebaseCallBack: FirebaseCallBack, refCharacters: DatabaseReference, refImage: DatabaseReference) {
        refCharacters.keepSynced(true)
        refCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    characterList[it.key.toString()] = it.getValue(Character::class.java)!!
                }
                firebaseCallBack.onCallback()
            }

            override fun onCancelled(error: DatabaseError) {
                println("ERROR")
            }
        })

        refImage.keepSynced(true)
        refImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.child("elements").children.forEach {
                    elementImageList[it.key.toString()] = it.getValue(ElementImage::class.java)!!
                }
                snapshot.child("characters").children.forEach {
                    characterImageList[it.key.toString()] = it.getValue(CharacterImage::class.java)!!
                }
                firebaseCallBack.onCallback()
            }

            override fun onCancelled(error: DatabaseError) {
                println("ERROR")
            }
        })

    }



    private fun initRecycler() {
        characterAdapter = CharacterAdapter(characters)
        characterAdapter!!.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualCharacterActivity::class.java).apply {
                    putExtra("character", characterList.getValue(defaultName))
                    putExtra("characterImage", characterImageList.getValue(defaultName))
                    putExtra("elementImageList", elementImageList)
                    putExtra("language", language)
                    putExtra("defaultName", defaultName)
                }
                startActivity(intent)
            }
        })
        rvCharacters?.adapter = characterAdapter
        val columns = (((context?.resources?.displayMetrics?.widthPixels))?.div(270))
        rvCharacters?.layoutManager = columns?.let { GridLayoutManager(context, it) }

    }

    private fun fillCharacters(characterListSorted: MutableMap<String, Character>) {
        characterListSorted.keys.forEach {
            characters.add(
                CharacterImageName(
                    it,
                    characterList[it]?.name,
                    characterList[it]?.element,
                    characterList[it]?.weapontype,
                    characterList[it]?.region,
                    characterImageList[it]?.icon
                )
            )
        }
        initRecycler()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        characterAdapter?.filterName(query!!)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        characterAdapter?.filterName(newText!!)
        return false
    }
}
