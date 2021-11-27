package com.example.genshinimpactkotlin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshinimpactkotlin.R;
import com.example.genshinimpactkotlin.adapters.PersonajesAdapter;
import com.example.genshinimpactkotlin.clases.PersonajeVo;

import java.util.ArrayList;

public class ListaPersonajesFragment extends Fragment {

    RecyclerView recyclerPersonajes;
    ArrayList<PersonajeVo> listaPersonajes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_lista_personajes, container, false);
        listaPersonajes = new ArrayList<>();
        recyclerPersonajes = (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerPersonajes.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();
        PersonajesAdapter adapter = new PersonajesAdapter(listaPersonajes);
        recyclerPersonajes.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Selecciona: " + listaPersonajes.get(recyclerPersonajes.getChildAdapterPosition(vista)).getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

    private void llenarLista() {
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));
        listaPersonajes.add(new PersonajeVo("Goku", "Aguacates todos los días", R.drawable.ic_launcher_background));

    }
}