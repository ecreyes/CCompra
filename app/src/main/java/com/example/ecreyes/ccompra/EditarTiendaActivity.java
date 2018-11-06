package com.example.ecreyes.ccompra;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ecreyes.ccompra.Objetos.FirebaseReferences;
import com.example.ecreyes.ccompra.Objetos.Tienda;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditarTiendaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database_ref = FirebaseDatabase.getInstance();
        DatabaseReference tiendaRef = database_ref.getReference(FirebaseReferences.TIENDA_REFERENCES);
        Intent intent = new Intent(this,UserProfileActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tienda);
        Spinner spinner;
        spinner = findViewById(R.id.categoria_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoria_array, android.R.layout.simple_spinner_item);
        String nombre;
        String descripcion;
        String categoria;
        String uri;
        String email;
        Button boton;
        boton = findViewById(R.id.btnTienda);
        EditText nombre_tienda;
        EditText descripcion_tienda;
        descripcion_tienda =findViewById(R.id.descripcionTienda);
        spinner.setAdapter(adapter);
        nombre_tienda = findViewById(R.id.nombreTienda);
        int id_tienda = getIntent().getIntExtra("tienda",122);
        Integer lol = id_tienda;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Tienda");
        database.orderByChild("id").equalTo(id_tienda).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()){
                    try{
                        Tienda tienda = snapshot.getValue(Tienda.class);
                        String nombre = tienda.getNombre();
                        nombre_tienda.setText(nombre.toString());
                        String descripcion = tienda.getDescripcion();
                        descripcion_tienda.setText(descripcion);
                        String email = tienda.getEmail();
                        String uri = tienda.getUri();
                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Query query = database.orderByChild("id").equalTo(id_tienda);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot datos: dataSnapshot.getChildren()){
                                            datos.getRef().removeValue();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                Tienda tienda = new Tienda(id_tienda,true,nombre_tienda.getText().toString(),descripcion_tienda.getText().toString(),
                                        uri,email,spinner.getSelectedItem().toString());
                                tiendaRef.push().setValue(tienda);
                                startActivity(intent);
                            }
                        });
                    }catch (Exception e){
                        Log.e("Error ","Error");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
