package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import com.example.genshinimpactkotlin.R
import com.example.genshinimpactkotlin.dto.*

import com.squareup.picasso.Picasso

class WeaponIndividualInfoFragment : Fragment() {
    var weapon: Weapon = Weapon()
    var weaponImage: WeaponImage = WeaponImage()


    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weapon = arguments?.get("weapon") as Weapon
        weaponImage = arguments?.get("weaponImage") as WeaponImage
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_weapon_individual_info, container, false);
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillWeapon(view)
    }

    private fun fillWeapon(view: View) {
        view.findViewById<TextView>(R.id.indWeapon_tvWeaponName).text = weapon.name
        view.findViewById<TextView>(R.id.indWeapon_tvType).text = weapon.weapontype
        view.findViewById<TextView>(R.id.indWeapon_tvRarity).text = weapon.rarity
        view.findViewById<TextView>(R.id.indWeapon_tvBaseAttack).text = weapon.baseatk.toString()
        view.findViewById<TextView>(R.id.indWeapon_tvSubStat).text = weapon.substat
        view.findViewById<TextView>(R.id.indWeapon_tvDescription).text = weapon.description
        if (!weapon.effectname.isNullOrBlank()) {
            var effect = weapon.effect
            weapon.r1?.forEachIndexed { i, element ->
                effect = effect?.replace("{$i}", element)
            }
            view.findViewById<TextView>(R.id.indWeapon_tvEffectName).text = weapon.effectname
            view.findViewById<TextView>(R.id.indWeapon_tvEffect).text = effect
        } else {
            view.findViewById<TextView>(R.id.indWeapon_tvEffectName).visibility = View.GONE
            view.findViewById<TextView>(R.id.indWeapon_tvEffect).visibility = View.GONE
        }
        Picasso.get().load(weaponImage.icon).into(view.findViewById<ImageView>(R.id.indWeapon_ivWeaponImage))
    }

}
