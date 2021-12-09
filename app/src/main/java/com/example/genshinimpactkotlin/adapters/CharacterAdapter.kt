package com.example.genshinimpactkotlin.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.entidades.CharacterImageName
import com.example.genshinimpactkotlin.R
import com.squareup.picasso.Picasso
import java.util.ArrayList
import java.util.stream.Collectors


class CharacterAdapter(
    private val characters: ArrayList<CharacterImageName>
):

    RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {

    private lateinit var mListener : onItemClickListener
    private var originalCharacters: ArrayList<CharacterImageName> = ArrayList(characters)

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

    @SuppressLint("NotifyDataSetChanged")
    fun filterName(name: String) {
        if (name.isEmpty()) {
            characters.clear()
            characters.addAll(originalCharacters)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                characters.clear()
                val lista: ArrayList<CharacterImageName> =
                    originalCharacters.stream().filter { i -> i.languageName?.lowercase()!!.contains(name) }.collect(
                        Collectors.toList()) as ArrayList<CharacterImageName>

                characters.addAll(lista)
            } else {
                originalCharacters.forEach {
                    if (it.languageName!!.lowercase().contains(name)) {
                        characters.add(it)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.render(characters[position], position)

    }

    class CharacterHolder(var view: View, listener: onItemClickListener):RecyclerView.ViewHolder(view) {
        var defaultName: String? = null
        var position: Int? = null
        fun render(characterImageName: CharacterImageName, position: Int) {
            this.position = position
            view.findViewById<TextView>(R.id.tvCharacterName)
                .text = characterImageName.languageName
            Picasso.get().load(characterImageName.icon)
                .into(view.findViewById<ImageView>(R.id.ivIconCharacter))
            defaultName = characterImageName.defaultName.toString()
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(defaultName!!, position!!)
            }
        }
    }
}
