package com.example.genshinimpactkotlin.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.genshinimpactkotlin.R

import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager


class MapFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val mapExternalAccept = sp.getBoolean("map", false)

        if (mapExternalAccept) {
            accessMap(view)
        } else {
            val dialog = context?.let {
                AlertDialog.Builder(it)
                    .setTitle(R.string.externalContent)
                    .setMessage(R.string.mapAlertDialog)
                    .setPositiveButton(R.string.accept) { viewDialog, _ ->
                        sp.edit().putBoolean("map", true).apply()
                        viewDialog.dismiss()
                        accessMap(view)
                    }.setNegativeButton(R.string.cancel) { view, _ ->
                        Toast.makeText(context, R.string.cancelled, Toast.LENGTH_LONG).show()
                        view.dismiss()
                    }
                    .setCancelable(false)
                    .create()
            }
            dialog?.show()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun accessMap(view: View) {
        val myWebView = view.findViewById<WebView>(R.id.map_interactiveMap)
        myWebView.webChromeClient = WebChromeClient()
        myWebView.apply {
            myWebView.loadUrl("https://mapgenie.io/genshin-impact/maps/teyvat")
            settings.javaScriptEnabled = true
        }
    }


}