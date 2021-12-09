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


class ItemTalentFragment : Fragment() {

    private var talentImage: String? = null
    private var talentName: String? = null
    private var talentInfo: String? = null


    /**
     * Carga los datos del bundle recibido en las variables locales
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            talentImage = it.getString("talentImage")
            talentName = it.getString("talentName")
            talentInfo = it.getString("talentInfo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_item_talent, container, false)
    }

    /**
     * Carga los datos en las views correspondientes
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ivTalent = view.findViewById<ImageView>(R.id.talent_ivTalentImage)
        val tvTalentName = view.findViewById<TextView>(R.id.talent_tvTalentName)
        val tvTalentInfo = view.findViewById<TextView>(R.id.talent_tvTalentInfo)

        Picasso.get().load(talentImage).into(ivTalent)
        tvTalentName.text = talentName
        tvTalentInfo.text = talentInfo


    }

    /**
     * Devuelve un fragmento con los parámetros pasados
     */
    companion object {
        @JvmStatic
        fun newInstance(talentImage: String, talentName: String, talentInfo: String): ItemTalentFragment {
            val fragment = ItemTalentFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putString("talentImage", talentImage)
                    putString("talentName", talentName)
                    putString("talentInfo", talentInfo)
                }
            }
            return fragment
        }
    }
}