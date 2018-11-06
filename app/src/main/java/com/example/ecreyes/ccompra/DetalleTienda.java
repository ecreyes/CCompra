package com.example.ecreyes.ccompra;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecreyes.ccompra.R;

public class DetalleTienda extends AppCompatActivity {
    TextView titulo;
    TextView ubicacion;
    TextView descripcion;
    ImageView imagen;
    Button estado;
    TextView alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tienda_layout);

        //back boton
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String keynombre = myIntent.getStringExtra("keynombre"); // will return "FirstKeyValue"
        String keycategoria = myIntent.getStringExtra("keycategoria");
        String keydescripcion = myIntent.getStringExtra("keydescripcion");
        String keyuri = myIntent.getStringExtra("keyuri");
        boolean keyestado = myIntent.getExtras().getBoolean("keyestado");
        String keyubicacion = myIntent.getStringExtra("keyubicacion");
        String keyalerta = myIntent.getStringExtra("keyalerta");

        titulo = findViewById(R.id.titulo_tienda);
        ubicacion = findViewById(R.id.lugar_tienda);
        descripcion = findViewById(R.id.descripcion_tienda);
        imagen = findViewById(R.id.imagen_tienda);
        alerta = findViewById(R.id.estado_tienda);


        titulo.setText(keynombre);
        descripcion.setText(keydescripcion);
        ubicacion.setText(keyubicacion);

        if (!keyestado){
            estado = findViewById(R.id.btn_abierto);
            estado.setText("Cerrado");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                estado.setBackground(this.getResources().getDrawable(R.drawable.button_shape_red));
            }
        }

        if (keyalerta!=null && keyalerta!=""){
            alerta.setText(keyalerta);
            alerta.setVisibility(View.VISIBLE);
        }
        else {
            alerta.setText("");
        }
        Glide.with(this)
                .load(keyuri)
                .into(imagen);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
