package com.example.genshinimpactkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.adapters.CharacterAdapter
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.clases.IndividualCharacterActivity
import com.example.genshinimpactkotlin.dto.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_characters, container, false);
        rvCharacters = view.findViewById(R.id.rvCharacters)
        
        // Se cargan en las variables la información recibida en los bundles
        language = arguments?.getString("language").toString()
        characterList = (arguments?.getSerializable("characterList") as HashMap<String, Character>?)!!
        characterImageList = (arguments?.getSerializable("characterImageList") as HashMap<String, CharacterImage>?)!!
        elementImageList = (arguments?.getSerializable("elementImageList") as HashMap<String, ElementImage>)

        // Esta acción es para que en el RecyclerView salgan los personajes ordenador por su nombre
        val characterListSorted: MutableMap<String, Character> = TreeMap(characterList)
        queryTalents()


        fillCharacters(characterListSorted)
        return view
    }

    private fun queryTalents() {
        val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        queryTalentsInfo(mDatabase)
        queryTalentsImage(mDatabase)
    }

    private fun initRecycler() {
        val adapter = CharacterAdapter(characters)
        adapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualCharacterActivity::class.java).apply {
                    putExtra("character", characterList.getValue(defaultName))
                    putExtra("characterImage", characterImageList.getValue(defaultName))
                    putExtra("elementImageList", elementImageList)
                    putExtra("talentsList", talentsList)
                    putExtra("talentsImagesList", talentsImagesList)
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


    private fun queryTalentsInfo(mDatabase: FirebaseDatabase) {
        mDatabase.getReference("Data/$language/talents")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        talentsList[it.key.toString()] = it.getValue(Talents::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun queryTalentsImage(mDatabase: FirebaseDatabase) {
        mDatabase.getReference("Image/talents")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        talentsImagesList[it.key.toString()] = it.getValue(TalentsImages::class.java)!!
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

}
