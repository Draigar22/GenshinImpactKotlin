package com.example.genshinimpactkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.squareup.picasso.Picasso
import java.util.ArrayList



class CharacterAdapter(
    val characters: ArrayList<CharacterImageNameList>
):

    RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {


    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(defaultName: String, position: Int) {

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CharacterHolder(
                layoutInflater.inflate(R.layout.recycler_character_item, parent, false),
                mListener)


    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.render(characters[position], position)

    }

    class CharacterHolder(var view: View, listener: onItemClickListener):RecyclerView.ViewHolder(view) {
        var defaultName: String? = null
        var position: Int? = null
        fun render(characterImageNameList: CharacterImageNameList, position: Int) {
            this.position = position
            view.findViewById<TextView>(R.id.tvCharacterName)
                .text = characterImageNameList.languageName
            Picasso.get().load(characterImageNameList.icon)
                .into(view.findViewById<ImageView>(R.id.ivIconCharacter))
            defaultName = characterImageNameList.defaultName.toString()
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(defaultName!!, position!!)
            }
        }
    }
}
