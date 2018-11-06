package com.example.ecreyes.ccompra;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecreyes.ccompra.Objetos.Tienda;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListaTiendasProfileAdapter extends RecyclerView.Adapter<ListaTiendasProfileAdapter.TiendaHolder> {

    List<Tienda> tiendas;

    public ListaTiendasProfileAdapter(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public TiendaHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_tiendas, parent, false);
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

    public  class TiendaHolder extends RecyclerView.ViewHolder {

        TextView textViewTienda;
        Switch mswitch;
        TextView textViewEliminar;

        public TiendaHolder(View itemView)  {
            super(itemView);

            textViewTienda = (TextView) itemView.findViewById(R.id.textview_tiendas);
            textViewEliminar = (TextView) itemView.findViewById(R.id.elim_tienda);
            textViewEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Tienda");
                    Integer pos = getAdapterPosition();
                    Tienda tiendap = tiendas.get(pos);
                    Log.e("Clickeadooo","Gran Tejedora");
                    Query query = database.orderByChild("id").equalTo(tiendap.getId());
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
                }
            });
            mswitch = (Switch) itemView.findViewById(R.id.estado_switch);
            mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Tienda");
                    Integer pos = getAdapterPosition();
                    Tienda tiendap = tiendas.get(pos);
                    if (isChecked) {
                        Log.e("ExistenciaSi", tiendap.getNombre());
                        Query query = database.orderByChild("id").equalTo(tiendap.getId());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot child: dataSnapshot.getChildren()){
                                    child.getRef().child("estado").setValue(true);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                   } else {
                        Log.e("ExistenciaNO", "Gran Tejedora");
                        Query query = database.orderByChild("id").equalTo(tiendap.getId());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot child: dataSnapshot.getChildren()){
                                    child.getRef().child("estado").setValue(false);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }
    }
}




