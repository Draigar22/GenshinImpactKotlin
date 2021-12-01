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



    // TODO: Rename and change types of parameters
    private var talentImage: String? = null
    private var talentName: String? = null
    private var talentInfo: String? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ivTalent = view.findViewById<ImageView>(R.id.talent_ivTalentImage)
        val tvTalentName = view.findViewById<TextView>(R.id.talent_tvTalentName)
        val tvTalentInfo = view.findViewById<TextView>(R.id.talent_tvTalentInfo)


        Picasso.get().load(talentImage).into(ivTalent)
        tvTalentName.text = talentName
        tvTalentInfo.text = talentInfo


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param talentImage Parameter 1.
         * @param talentName Parameter 2.
         *  @param talentInfo Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
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