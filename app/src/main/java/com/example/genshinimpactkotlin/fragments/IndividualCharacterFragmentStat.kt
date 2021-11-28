package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class IndividualCharacterFragmentStat : Fragment() {
    var defaultName: String = "defaultName"
    val mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val characters: ArrayList<CharacterImageNameList> = arrayListOf()
    val allCharacters: ArrayList<String> = arrayListOf()
    val namesCharacters: ArrayList<String> = arrayListOf()
    val avatarCharacters: ArrayList<String> = arrayListOf()
    var rvCharacters: RecyclerView? = null
    val elementCharacters: ArrayList<String> =
        arrayListOf() // TODO // HACER ARRAY ELEMENTOS Y TIPOARMA O ADAPTAR
    var language = "language" // TODO IMPLEMENTAR FUNCIONALIDAD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_character_individual_stat, container, false);
        defaultName = arguments?.getString(defaultName).toString()
        language = arguments?.getString(language).toString()
        println(language)
        println(defaultName)
        startQuerys(
            mDatabase.getReference("Data/$language/characters/$defaultName"),
            mDatabase.getReference("Image/characters/$defaultName"), view
        )
        return view
    }

    /**
     * Recibe un snapshot para hacer la query correspondiente para devolver un arraylist de strings
     * con los nombres (estos serán como claves ya que tienen el mismo nombre en otras rutas)
     */
    private fun queryCharacter(snapshot: DataSnapshot, view: View) {
        view.findViewById<TextView>(R.id.ind_tvCharacterName).text = snapshot.child("name").value.toString()
        view.findViewById<TextView>(R.id.ind_tvCharacterRegion).text = snapshot.child("region").value.toString()
        view.findViewById<TextView>(R.id.ind_tvCharacterWeaponType).text = snapshot.child("weapontype").value.toString()
        view.findViewById<TextView>(R.id.ind_tvCharacterSubStat).text = snapshot.child("substat").value.toString()
        view.findViewById<TextView>(R.id.ind_tvCharacterElement).text = snapshot.child("element").value.toString()
    }



    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todas las direcciones de los avatares
     */
    private fun queryCharacterImage(snapshot: DataSnapshot, view: View) {
        Picasso.get().load(snapshot.child("cover2").value.toString())
            .into(view.findViewById<ImageView>(R.id.ind_ivCharacterImage))
    }


    /**
     *
     * Se encarga de llamar a los métodos necesarios para rellenar @param="characters:ArrayList"
     * con todos los nombres de los personajes e iconos correspondiente
     *
     */
    private fun startQuerys(
        refCharacters: DatabaseReference,
        refImagesCharacters: DatabaseReference,
        view: View
    ) {

        refCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryCharacter(snapshot, view)
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })

        refImagesCharacters.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                queryCharacterImage(snapshot, view)
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO HACER ONCANCELLED
            }
        })

    }
}
