package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.entidades.*

import com.squareup.picasso.Picasso

class CharacterIndividualInfoFragment : Fragment() {
    private var character: Character = Character()
    private var characterImage: CharacterImage = CharacterImage()
    private var elementListImage: HashMap <String,ElementImage> = hashMapOf()


    @Suppress("UNCHECKED_CAST")
    /**
     * Carga los datos de arguments en las variables locales
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        character = arguments?.get("character") as Character
        characterImage = arguments?.get("characterImage") as CharacterImage
        elementListImage = arguments?.get("elementImageList") as HashMap<String, ElementImage>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_character_individual_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillCharacter(view)
    }

    /**
     * Carga los datos de "character" en las views correspondientes
     */
    private fun fillCharacter(view: View) {
        view.findViewById<TextView>(R.id.indCharacter_tvCharacterName).text = character.name
        view.findViewById<TextView>(R.id.indCharacter_tvCharacterRegion).text = character.region
        view.findViewById<TextView>(R.id.indCharacter_tvCharacterWeaponType).text = character.weapontype
        view.findViewById<TextView>(R.id.indCharacter_tvCharacterSubStat).text = character.substat
        view.findViewById<TextView>(R.id.indCharacter_tvCharacterElement).text = character.element
        view.findViewById<TextView>(R.id.indCharacter_tvDescription).text = character.description
        Picasso.get().load(searchElement()).into(view.findViewById<ImageView>(R.id.indCharacter_ivElement))
        if (!characterImage.cover2.isNullOrBlank())
            Picasso.get().load(
                characterImage.cover2).into(view.findViewById<ImageView>(R.id.indCharacter_ivCharacterImage))
        else
            Picasso.get().load(
                characterImage.portrait).into(view.findViewById<ImageView>(R.id.indCharacter_ivCharacterImage))
    }

    /**
     * Busca en "elementListImage" el elemento correspondiente con "character" y devuelve el valor
     * de wikia, que es una url de una imagen
     */
    private fun searchElement(): String? {
        elementListImage.keys.forEach {
            if (it == character.element?.lowercase()) {
                return elementListImage[it]?.wikia
            }
        }
        return elementListImage.getValue("cryo").wikia
    }

}
