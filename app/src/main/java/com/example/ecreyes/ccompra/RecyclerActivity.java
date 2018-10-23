package com.example.ecreyes.ccompra;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ecreyes.ccompra.Objetos.Categoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    RecyclerView rv;
    List<Categoria> categorias;
    ListaCategoriaAdapter listaCategoriaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        rv = (RecyclerView) findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        categorias = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        listaCategoriaAdapter = new ListaCategoriaAdapter(categorias);
        rv.setAdapter(listaCategoriaAdapter);

        database.getReference("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categorias.removeAll(categorias);
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()){
                    try {
                        Categoria categoria = snapshot.getValue(Categoria.class);
                        categorias.add(categoria);

                    } catch (Exception e){
                        Log.e("ERRROR","222222");
                    }
                    Log.e(String.valueOf(categorias.size()),"2232");
                }
                listaCategoriaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
