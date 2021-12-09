package com.example.genshinimpactkotlin.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.genshinimpactkotlin.R
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate


class SettingsFragment : PreferenceFragmentCompat() {

    private var preferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    /**
     * Cada vez que hay un cambio en "Opciones" y este concuerda con el apartado "Tema" cambia
     * automáticamente a true||false cambiando el tema de la aplicación
     * (de forma predeterminada está el tema oscuro)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceChangeListener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                println(context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK))
                if (key.equals("theme")) {
                    if (sharedPreferences.getBoolean(key, false))
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

            }

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }


}