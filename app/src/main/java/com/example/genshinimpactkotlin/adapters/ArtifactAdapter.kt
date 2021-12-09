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
import com.example.genshinimpactkotlin.entidades.ArtifactImageName
import com.squareup.picasso.Picasso
import java.util.ArrayList
import java.util.stream.Collectors


class ArtifactAdapter(val artifacts: ArrayList<ArtifactImageName>):
    RecyclerView.Adapter<ArtifactAdapter.WeaponHolder>() {


    private var originalArtifacts: ArrayList<ArtifactImageName> = ArrayList(artifacts)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return WeaponHolder(
                layoutInflater.inflate(R.layout.recycler_artifact_item, parent, false))
    }

    override fun getItemCount(): Int = artifacts.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterName(name: String) {
        if (name.isEmpty()) {
            artifacts.clear()
            artifacts.addAll(originalArtifacts)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                artifacts.clear()
                val lista: ArrayList<ArtifactImageName> =
                    originalArtifacts.stream().filter { i -> i.languageName?.lowercase()!!.contains(name) }.collect(
                        Collectors.toList()) as ArrayList<ArtifactImageName>

                artifacts.addAll(lista)
            } else {
                originalArtifacts.forEach {
                    if (it.languageName!!.lowercase().contains(name)) {
                        artifacts.add(it)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WeaponHolder, position: Int) {
        holder.render(artifacts[position], position)
    }


    class WeaponHolder(var view: View):RecyclerView.ViewHolder(view) {
        var defaultName: String? = null
        var position: Int? = null
        fun render(artifact: ArtifactImageName, position: Int) {
            this.position = position
            view.findViewById<TextView>(R.id.tvArtifactName)
                .text = artifact.languageName
            if (artifact.`1pc`.isNullOrBlank()) {
                visibilityEffectsTwoFourPiece(view)
                view.findViewById<TextView>(R.id.tvTwoPcEffect).text = artifact.`2pc`
                view.findViewById<TextView>(R.id.tvFourPcEffect).text = artifact.`4pc`
            } else {
                visibilityEffectsOnePiece(view)
                view.findViewById<TextView>(R.id.tvOnePcEffect).text = artifact.`1pc`
            }
            Picasso.get().load(artifact.icon)
                .into(view.findViewById<ImageView>(R.id.ivIconArtifact))
            defaultName = artifact.defaultName.toString()
        }
        fun visibilityEffectsOnePiece(view: View) {
            view.findViewById<TextView>(R.id.tvTwoPcEffect1).visibility = View.GONE
            view.findViewById<TextView>(R.id.tvFourPcEffect1).visibility = View.GONE
            view.findViewById<TextView>(R.id.tvTwoPcEffect).visibility = View.GONE
            view.findViewById<TextView>(R.id.tvFourPcEffect).visibility = View.GONE
            view.findViewById<TextView>(R.id.tvOnePcEffect).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvOnePcEffect1).visibility = View.VISIBLE
        }
        fun visibilityEffectsTwoFourPiece(view: View) {
            view.findViewById<TextView>(R.id.tvTwoPcEffect1).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvFourPcEffect1).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvTwoPcEffect).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvFourPcEffect).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvOnePcEffect).visibility = View.GONE
            view.findViewById<TextView>(R.id.tvOnePcEffect1).visibility = View.GONE
        }
    }


}
