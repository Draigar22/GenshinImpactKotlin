package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.genshinimpactkotlin.R

import com.squareup.picasso.Picasso
import com.example.genshinimpactkotlin.dto.Character
import com.example.genshinimpactkotlin.dto.CharacterImage
import com.example.genshinimpactkotlin.dto.ElementImage

class IndividualCharacterFragmentStat : Fragment() {
    var character: Character = Character()
    var characterImage: CharacterImage = CharacterImage()
    var elementListImage: ArrayList<ElementImage> = arrayListOf()
    var ele:String = "hola"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_character_individual_stat, container, false);
        character = arguments?.get("character") as Character
        characterImage = arguments?.get("characterImage") as CharacterImage
        elementListImage = arguments?.getParcelableArrayList<ElementImage>("elementImageList") as ArrayList<ElementImage>

        fillCharacter(view)
        ele = character.element.toString()
        return view
    }

    private fun fillCharacter(view: View) {
        view.findViewById<TextView>(R.id.ind_tvCharacterName).text = character.name
        view.findViewById<TextView>(R.id.ind_tvCharacterRegion).text = character.region
        view.findViewById<TextView>(R.id.ind_tvCharacterWeaponType).text = character.weapontype
        view.findViewById<TextView>(R.id.ind_tvCharacterSubStat).text = character.substat
        view.findViewById<TextView>(R.id.ind_tvCharacterElement).text = character.element
        Picasso.get().load(searchElement()).into(view.findViewById<ImageView>(R.id.ind_ivElement))
        if (!characterImage.cover2.isNullOrBlank())
            Picasso.get().load(characterImage.cover2).into(view.findViewById<ImageView>(R.id.ind_ivCharacterImage))
        else
            Picasso.get().load(characterImage.portrait).into(view.findViewById<ImageView>(R.id.ind_ivCharacterImage))
    }

    private fun searchElement(): String? {
        elementListImage.forEachIndexed { i, elementList ->
            if (elementList.name?.lowercase() == character.element?.lowercase()) {
                return elementListImage[i].wikia
            }
        }
        return "papa"
    }

}
