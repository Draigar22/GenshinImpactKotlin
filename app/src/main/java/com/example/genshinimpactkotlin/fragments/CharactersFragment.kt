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
import com.example.genshinimpactkotlin.dto.Character
import com.example.genshinimpactkotlin.dto.CharacterImage
import com.example.genshinimpactkotlin.dto.ElementImage
import java.util.*
import kotlin.collections.HashMap

class CharactersFragment : Fragment() {
    val characters: ArrayList<CharacterImageNameList> = arrayListOf()
    var rvCharacters: RecyclerView? = null
    var characterList: HashMap<String, Character> = hashMapOf()
    var characterImageList: HashMap<String, CharacterImage> = hashMapOf()
    var elementImageList: HashMap<String, ElementImage> = hashMapOf()
    val elementCharacters: ArrayList<String> =
        arrayListOf() // TODO // HACER ARRAY ELEMENTOS Y TIPOARMA O ADAPTAR
    var language = "Spanish" // TODO IMPLEMENTAR FUNCIONALIDAD

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_characters, container, false);
        rvCharacters = view.findViewById(R.id.rvCharacters)
        characterList = (arguments?.getSerializable("characterList") as HashMap<String, Character>?)!!
        characterImageList = (arguments?.getSerializable("characterImageList") as HashMap<String, CharacterImage>?)!!
        elementImageList = (arguments?.getSerializable("elementImageList") as HashMap<String, ElementImage>)
        val characterListSorted: MutableMap<String, Character> = TreeMap(characterList)


        fillCharacters(characterListSorted)
        return view
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

    private fun initRecycler() {
        val adapter = CharacterAdapter(characters)
        adapter.setOnItemClickListener(object : CharacterAdapter.onItemClickListener {
            override fun onItemClick(defaultName: String, position: Int) {

                val intent = Intent(context, IndividualCharacterActivity::class.java).apply {
                    putExtra("character", characterList.getValue(defaultName))
                    putExtra("characterImage", characterImageList.getValue(defaultName))
                    putExtra("elementImageList", elementImageList)
                }
                startActivity(intent)
            }
        })
        rvCharacters?.adapter = adapter
        val columns = (((resources.displayMetrics.widthPixels  ))/200)-1;
        rvCharacters?.layoutManager = GridLayoutManager(context, columns)
    }

}
