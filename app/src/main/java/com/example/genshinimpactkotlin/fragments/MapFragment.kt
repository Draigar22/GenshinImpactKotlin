package com.example.genshinimpactkotlin.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import com.example.genshinimpactkotlin.R
import android.widget.Button

import android.view.WindowManager

import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class MapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView = view.findViewById<WebView>(R.id.map_interactiveMap)
        myWebView.webChromeClient = WebChromeClient()
        myWebView.apply {
            myWebView.loadUrl("https://mapgenie.io/genshin-impact/maps/teyvat")
            settings.javaScriptEnabled = true
        }
        if (!(arguments?.get("alertDialog") as Boolean)) {
            val dialog = context?.let {
                AlertDialog.Builder(it)
                    .setTitle("Contenido externo")
                    .setMessage(R.string.mapAlertDialog)
                    .setPositiveButton("Vale") { view, _ ->
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()
            }

            dialog?.show()
        }
    }


}