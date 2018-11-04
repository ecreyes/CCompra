package com.example.ecreyes.ccompra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecreyes.ccompra.Objetos.Tienda;
import com.example.ecreyes.ccompra.TiendasFragment.OnFragmentInteractionListener;
import com.example.ecreyes.ccompra.dummy.DummyContent.DummyItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TiendasRecyclerViewAdapter extends RecyclerView.Adapter<TiendasRecyclerViewAdapter.ViewHolder>
    implements View.OnClickListener {

    private ArrayList<Tienda> mTiendas;
    Context mContext;
    private OnFragmentInteractionListener mListener;

    private View.OnClickListener listener;

    public TiendasRecyclerViewAdapter(ArrayList<Tienda> tiendas, Context mContext) {
        this.mTiendas = tiendas;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tiendas, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.t_name.setText(mTiendas.get(position).getNombre());
        if (mTiendas.get(position).isEstado()){
            holder.t_status.setText("Abierto");
        }
        else {
            holder.t_status.setText("Cerrado");
        }
        Glide.with(mContext).load(mTiendas.get(position).getUri()).into(holder.t_img);

        /* holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }

        });*/
    }

    @Override
    public int getItemCount() {
        return mTiendas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //public final View mView;
        public final TextView t_name;
        public final TextView t_status;
        public ImageView t_img;

        public ViewHolder(View view) {
            super(view);
            //mView = view;

            t_name = (TextView) view.findViewById(R.id.t_name);
            t_status = (TextView) view.findViewById(R.id.t_status);
            t_img = (ImageView) itemView.findViewById(R.id.miniTienda);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}
