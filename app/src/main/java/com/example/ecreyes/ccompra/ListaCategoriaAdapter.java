package com.example.ecreyes.ccompra;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecreyes.ccompra.Objetos.Categoria;

import java.util.List;

public class ListaCategoriaAdapter extends RecyclerView.Adapter<ListaCategoriaAdapter.CategoriaHolder> {

    List<Categoria> categorias;

    public ListaCategoriaAdapter(List<Categoria> categorias) {
        this.categorias  = categorias;
    }

    @Override
    public CategoriaHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent,false);
        CategoriaHolder holder = new CategoriaHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoriaHolder categoriaHolder, int position) {
        Categoria categoria = categorias.get(position);
        categoriaHolder.textViewCategoria.setText(categoria.getNombre());

    }



    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public  static  class CategoriaHolder   extends RecyclerView.ViewHolder {

        TextView textViewCategoria;

        public CategoriaHolder(View itemView){
            super(itemView);
            textViewCategoria = (TextView) itemView.findViewById(R.id.textview_categoria);
        }
    }
}
