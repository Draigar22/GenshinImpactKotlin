package com.example.genshinimpactkotlin

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

class CharacterAdapter(val character:List<CharacterImageNameList>):
    RecyclerView.Adapter<CharacterAdapter.CharacterHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CharacterHolder(layoutInflater.inflate(R.layout.item_character, parent, false))
        }

        override fun getItemCount(): Int = character.size

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.render(character[position])
    }

    class CharacterHolder(var view: View):RecyclerView.ViewHolder(view) {

        fun render(characterImageNameList: CharacterImageNameList) {
            view.findViewById<TextView>(R.id.tvCharacterName)
                .text = characterImageNameList.characterName
            Picasso.get().load(characterImageNameList.iconCharacter)
                .into(view.findViewById<ImageView>(R.id.ivIconCharacter))
        }
    }
}
