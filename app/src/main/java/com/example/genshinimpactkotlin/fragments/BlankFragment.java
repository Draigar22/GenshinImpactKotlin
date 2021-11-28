package com.example.genshinimpactkotlin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.genshinimpactkotlin.R;
import com.example.genshinimpactkotlin.dto.Character;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    FirebaseDatabase mDatabase  = FirebaseDatabase.getInstance();
    Character personaje;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDatabase.getReference("Data/Spanish/characters/diluc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                personaje = snapshot.getValue(Character.class);
                System.out.println(personaje.getBirthday());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return inflater.inflate(R.layout.fragment_characters, container, false);


    }
}