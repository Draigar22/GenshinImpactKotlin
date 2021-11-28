package com.example.genshinimpactkotlin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.adapters.CharacterAdapter
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.clases.IndividualCharacterActivity
import com.example.genshinimpactkotlin.dto.Character
import com.example.genshinimpactkotlin.dto.CharacterImage
import com.example.genshinimpactkotlin.dto.ElementImage

class CharactersFragment : Fragment() {
    val characters: ArrayList<CharacterImageNameList> = arrayListOf()
    var rvCharacters: RecyclerView? = null
    var characterList: ArrayList<Character> = arrayListOf()
    var characterImageList: ArrayList<CharacterImage> = arrayListOf()
    var elementImageList: ArrayList<ElementImage> = arrayListOf()
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
        characterList = arguments?.getParcelableArrayList("characterList")!!
        characterImageList = arguments?.getParcelableArrayList("characterImageList")!!
        elementImageList = arguments?.getParcelableArrayList("elementImageList")!!

        fillCharacters()
        return view
    }


    private fun fillCharacters() {
        for (num in 0 until characterList.size) {
                characters.add(
                    CharacterImageNameList(
                        characterList[num].defaultName,
                        characterList[num].name,
                        characterList[num].element,
                        characterList[num].weapontype,
                        characterList[num].region,
                        characterImageList[num].icon
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
                    putExtra("character", characterList[position])
                    putExtra("characterImage", characterImageList[position])
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
