package com.example.genshinimpactkotlin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshinimpactkotlin.R;
import com.example.genshinimpactkotlin.clases.PersonajeVo;

import java.util.ArrayList;

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajesAdapter.PersonajeViewHolder>
        implements View.OnClickListener {

    ArrayList<PersonajeVo> listaPersonaje;
    private View.OnClickListener listener;

    public PersonajesAdapter(ArrayList<PersonajeVo> listaPersonaje) {
        this.listaPersonaje = listaPersonaje;
    }

    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        view.setOnClickListener(this);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        holder.txtNombre.setText(listaPersonaje.get(position).getNombre());
        holder.txtInformacion.setText(listaPersonaje.get(position).getInfo());
        holder.foto.setImageResource(listaPersonaje.get(position).getImagenId());
    }

    @Override
    public int getItemCount() {
        return listaPersonaje.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null) {
            listener.onClick(view);
        }
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtInformacion;
        ImageView foto;

        public PersonajeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.idNombre);
            txtInformacion = (TextView) itemView.findViewById(R.id.idInfo);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
