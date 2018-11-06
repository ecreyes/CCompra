package com.example.ecreyes.ccompra;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecreyes.ccompra.Objetos.Tienda;

import java.util.List;

public class ListaTiendasProfileAdapter extends RecyclerView.Adapter<ListaTiendasProfileAdapter.TiendaHolder> {

    List<Tienda> tiendas;

    public ListaTiendasProfileAdapter(List<Tienda> tiendas) {
        this.tiendas  = tiendas;
    }

    public TiendaHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_tiendas,parent,false);
        TiendaHolder holder = new TiendaHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TiendaHolder tiendaHolder, int position) {
        Tienda tienda = tiendas.get(position);
        tiendaHolder.textViewTienda.setText(tienda.getNombre());
    }

    @Override
    public int getItemCount() {
        return tiendas.size();
    }

    public  static  class TiendaHolder   extends RecyclerView.ViewHolder {

        TextView textViewTienda;

        public TiendaHolder(View itemView){
            super(itemView);
            textViewTienda = (TextView) itemView.findViewById(R.id.textview_tiendas);
        }
    }
}




