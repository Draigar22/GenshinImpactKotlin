package com.example.genshinimpactkotlin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshinimpactkotlin.clases.CharacterImageNameList;
import com.example.genshinimpactkotlin.R;
import com.example.genshinimpactkotlin.adapters.CharacterAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CharactersFragmentJava extends Fragment {
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    ArrayList<CharacterImageNameList> characters = new ArrayList<>();
    ArrayList<String> allCharacters = new ArrayList<>();
    ArrayList<String> namesCharacters = new ArrayList<>();
    ArrayList<String> avatarCharacters = new ArrayList<>();
    RecyclerView rvCharacters;
    ArrayList<String> elementCharacters; // TODO // HACER ARRAY ELEMENTOS Y TIPOARMA O ADAPTAR
    String language = "Spanish"; // TODO IMPLEMENTAR FUNCIONALIDAD

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_characters, container, false);
        rvCharacters = view.findViewById(R.id.rvCharacters);
        startQuerys(
                mDatabase.getReference("Data/" + language + "/characters"),
                mDatabase.getReference("Image/characters"));

        return view;

    }

    private void fillCharacters() {
        for (int i = 0; i < allCharacters.size(); i++) {
            characters.add(
                    new CharacterImageNameList(
                            allCharacters.get(i),
                            namesCharacters.get(i),
                            avatarCharacters.get(i)));
        }

    }

    private void initRecycler() {
        CharacterAdapter adapter = new CharacterAdapter(characters);
        adapter.setOnItemClickListener(new CharacterAdapter.onItemClickListener() {
            @Override
            public void onItemClick(@NonNull String defaultName) {
                Toast.makeText(rvCharacters.getContext(), defaultName, Toast.LENGTH_SHORT).show();
            }
        });

        rvCharacters.setAdapter(adapter);
        int columns = (((getResources().getDisplayMetrics().widthPixels))/200 );
        rvCharacters.setLayoutManager(new GridLayoutManager(getContext(), columns));
    }

    /**
     * Recibe un snapshot para hacer la query correspondiente para devolver un arraylist de strings
     * con los nombres (estos serán como claves ya que tienen el mismo nombre en otras rutas)
     */
    private void queryCharacters(DataSnapshot snapshot) {
        for (DataSnapshot character : snapshot.getChildren()) {
            allCharacters.add(character.getKey());
        }
    }



    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todas las direcciones de los avatares
     */
    private void queryAvatarCharacters(DataSnapshot snapshot ) {
        for (int i = 0; i < allCharacters.size(); i++) {
            avatarCharacters.add(snapshot.child(allCharacters.get(i)).child("icon").getValue().toString());
        }

    }

    /**
     * Recibe un snapshot y un array con todos los personajes para devolver un arraylist de strings
     * con todos los nombres de los personajes
     */
    private void queryNamesCharacters( DataSnapshot snapshot) {
        for (int i = 0; i < allCharacters.size(); i++) {
            namesCharacters.add(
                    snapshot.child(allCharacters.get(i))
                            .child("name").getValue().toString());
        }
    }

    /**
     *
     * Se encarga de llamar a los métodos necesarios para rellenar @param="characters:ArrayList"
     * con todos los nombres de los personajes e iconos correspondiente
     *
     */
     private void startQuerys(DatabaseReference refCharacters,
                             DatabaseReference refImagesCharacters)
    {
        refCharacters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                queryCharacters(snapshot);
                queryNamesCharacters(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        refImagesCharacters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                queryAvatarCharacters(snapshot);
                fillCharacters();
                initRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
