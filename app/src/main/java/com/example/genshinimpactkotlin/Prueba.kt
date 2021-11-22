package com.example.genshinimpactkotlin

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class Prueba : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)
        val mDatabase: DatabaseReference
        val iv = findViewById<ImageView>(R.id.imageView2)
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("Image").child("characters").child("kamisatoayaka").child("icon")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
//                    System.out.println(childDataSnapshot.child("name").getValue());
//                    System.out.println("Josu es mal amigo :DDDDDDDD");
//                }
                    println(snapshot.value.toString())
                    Picasso.get().load(snapshot.value.toString()).into(iv)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}