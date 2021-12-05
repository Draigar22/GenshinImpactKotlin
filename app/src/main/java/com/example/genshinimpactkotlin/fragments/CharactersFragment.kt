package com.example.genshinimpactkotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.adapters.CharacterAdapter
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.clases.IndividualCharacterActivity
import com.example.genshinimpactkotlin.dto.*
import com.example.genshinimpactkotlin.interfaces.FirebaseCallBack
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

class CharactersFragment : Fragment() {
    val characters: ArrayList<CharacterImageNameList> = arrayListOf()
    var rvCharacters: RecyclerView? = null
    var characterList: HashMap<String, Character> = hashMapOf()
    var characterImageList: HashMap<String, CharacterImage> = hashMapOf()
    var elementImageList: HashMap<String, ElementImage> = hashMapOf()
    var talentsList: HashMap<String, Talents> = hashMapOf()
    var talentsImagesList: HashMap<String, TalentsImages> = hashMapOf()
    var language = "Spanish" // TODO IMPLEMENTAR FUNCIONALIDAD
    var searchView : SearchView? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language = arguments?.getString("language").toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_characters, container, false);
        rvCharacters = view.findViewById(R.id.rvCharacter)
        searchView = view.findViewById(R.id.searchView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
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
                // TODO HACER ONCANCELLED
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
                // TODO HACER ONCANCELLED
            }
        })

    }



    private fun initRecycler() {
        val adapter = CharacterAdapter(characters)
        adapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualCharacterActivity::class.java).apply {
                    putExtra("character", characterList.getValue(defaultName))
                    putExtra("characterImage", characterImageList.getValue(defaultName))
                    putExtra("elementImageList", elementImageList)
                    putExtra("language", language)
                    putExtra("defaultName", defaultName)
//                    putExtra("talents", talentsList.getValue(defaultName))
//                    putExtra("talentsImages", talentsImagesList.getValue(defaultName))
                }
                startActivity(intent)
            }
        })
        rvCharacters?.adapter = adapter
        val columns = (((resources.displayMetrics.widthPixels  ))/200)-1;
        rvCharacters?.layoutManager = GridLayoutManager(context, columns)
    }

    private fun fillCharacters(characterListSorted: MutableMap<String, Character>) {
        characterListSorted.keys.forEach {
            characters.add(
                CharacterImageNameList(
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
}
