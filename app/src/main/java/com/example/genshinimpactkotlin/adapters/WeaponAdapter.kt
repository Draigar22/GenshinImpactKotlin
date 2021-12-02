package com.example.genshinimpactkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.clases.CharacterImageNameList
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.clases.WeaponImageNameList
import com.squareup.picasso.Picasso
import java.util.ArrayList



class WeaponAdapter(
    val weapons: ArrayList<WeaponImageNameList>
):

    RecyclerView.Adapter<WeaponAdapter.WeaponHolder>() {


    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(defaultName: String, position: Int) {

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return WeaponHolder(
                layoutInflater.inflate(R.layout.recycler_weapon_item, parent, false),
                mListener)


    }

    override fun getItemCount(): Int = weapons.size

    override fun onBindViewHolder(holder: WeaponHolder, position: Int) {
        holder.render(weapons[position], position)

    }

    class WeaponHolder(var view: View, listener: onItemClickListener):RecyclerView.ViewHolder(view) {
        var defaultName: String? = null
        var position: Int? = null
        fun render(weaponImageNameList: WeaponImageNameList, position: Int) {
            this.position = position
            view.findViewById<TextView>(R.id.tvWeaponName)
                .text = weaponImageNameList.languageName
            Picasso.get().load(weaponImageNameList.icon)
                .into(view.findViewById<ImageView>(R.id.ivIconWeapon))
            defaultName = weaponImageNameList.defaultName.toString()
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(defaultName!!, position!!)
            }
        }
    }
}
