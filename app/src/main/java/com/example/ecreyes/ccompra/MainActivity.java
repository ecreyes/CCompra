package com.example.ecreyes.ccompra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecreyes.ccompra.Objetos.Categoria;
import com.example.ecreyes.ccompra.Objetos.FirebaseReferences;
import com.example.ecreyes.ccompra.Objetos.myCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    FirebaseDatabase database;
    DatabaseReference categoriaRef;
    Button botonCategoria;
    EditText etCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonCategoria = findViewById(R.id.btn_categoria);
        etCategoria = findViewById(R.id.et_categoria);

        database = FirebaseDatabase.getInstance();
        categoriaRef = database.getReference(FirebaseReferences.CATEGORIA_REFERENCES);
    }

    public void readData(myCallBack myCallback) {
        categoriaRef.child("idauto").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void pressCategoria(View view) {
        readData(new myCallBack() {
            @Override
            public void onCallback(int value) {
                categoriaRef.child("idauto").setValue(value+1);
                Categoria cat = new Categoria(value+1,etCategoria.getText().toString());
                categoriaRef.push().setValue(cat);
                etCategoria.setText("");
            }
        });
    }
}
