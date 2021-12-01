package com.example.genshinimpactkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.genshinimpactkotlin.R

class PopupFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bt = view?.findViewById<Button>(R.id.popup_button)
        bt?.setOnClickListener { onDestroy() }
        return inflater.inflate(R.layout.fragment_popup, container, false)
    }

}