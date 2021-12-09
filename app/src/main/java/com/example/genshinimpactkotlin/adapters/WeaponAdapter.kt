package com.example.genshinimpactkotlin.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.entidades.WeaponImageName
import com.squareup.picasso.Picasso
import java.util.ArrayList
import java.util.stream.Collectors


class WeaponAdapter(val weapons: ArrayList<WeaponImageName>):
    RecyclerView.Adapter<WeaponAdapter.WeaponHolder>() {


    private lateinit var mListener : onItemClickListener
    private var originalWeapons: ArrayList<WeaponImageName> = ArrayList(weapons)

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

    @SuppressLint("NotifyDataSetChanged")
    fun filterName(name: String) {
        if (name.isEmpty()) {
            weapons.clear()
            weapons.addAll(originalWeapons)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                weapons.clear()
                val lista: ArrayList<WeaponImageName> =
                    originalWeapons.stream().filter { i -> i.languageName?.lowercase()!!.contains(name) }.collect(
                        Collectors.toList()) as ArrayList<WeaponImageName>

                weapons.addAll(lista)
            } else {
                originalWeapons.forEach {
                    if (it.languageName!!.lowercase().contains(name)) {
                        weapons.add(it)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WeaponHolder, position: Int) {
        holder.render(weapons[position], position)
    }


    class WeaponHolder(var view: View, listener: onItemClickListener):RecyclerView.ViewHolder(view) {
        var defaultName: String? = null
        var position: Int? = null
        fun render(weaponImageName: WeaponImageName, position: Int) {
            this.position = position
            view.findViewById<TextView>(R.id.tvWeaponName)
                .text = weaponImageName.languageName
            Picasso.get().load(weaponImageName.icon)
                .into(view.findViewById<ImageView>(R.id.ivIconWeapon))
            defaultName = weaponImageName.defaultName.toString()
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(defaultName!!, position!!)
            }
        }
    }


}
