package com.example.ecreyes.ccompra;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ecreyes.ccompra.Objetos.FirebaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class ListaTiendasFragment extends Fragment {
    //Se declara la view
    View v;
    //variables
    ListView mylist;

    DatabaseReference refTienda = FirebaseDatabase.getInstance().getReference(FirebaseReferences.TIENDA_REFERENCES);
    Query q_tiendas;

    FirebaseStorage mstore;

    String text[] = new String[]{"Tienda 1",
            "Tienda 2",
            "Tienda 3",
            "Tienda 4",
            "Tienda 5",
            "Tienda 6",
            "Tienda 7"};
    int image[] = new int[]{R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda,
            R.drawable.pretienda};


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaTiendasFragment() {
        // Required empty public constructor
    }

    public static ListaTiendasFragment newInstance(String param1, String param2) {
        ListaTiendasFragment fragment = new ListaTiendasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_lista_tiendas, container, false);
        mylist = (ListView) v.findViewById(R.id.mylist);
        MyCustomListAdapter myadapter = new MyCustomListAdapter(getContext(), image, text);
        mylist.setAdapter((ListAdapter) myadapter);
        //q_tiendas = refTienda.getRef((FirebaseReferences.TIENDA_REFERENCES);
        final DatabaseReference TiendRef = FirebaseDatabase.getInstance().getReference("Tienda");
        Log.i("query", TiendRef.orderByChild("nombre").toString());
        getTiendasFromDB();

        mstore = FirebaseStorage.getInstance();
        Log.i("LISTA", "MALLL");

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void getTiendasFromDB(){

        refTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> nombresList = new ArrayList<String>();
                List<String> imagenesList = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Tienda tienda = ds.getValue(Tienda.class);
                    //tiendaList.add(tienda);
                    //System.out.print(tienda);
                    String Nombre = ds.child("nombre").getValue(String.class);
                    String Imagen = ds.child("uri").getValue(String.class);
                    Log.d("QUERY", "Imagen : " + Imagen + "   /" + Nombre + "/" );
                    nombresList.add(Nombre);
                    imagenesList.add(Imagen);

                }
                Log.i("Lista--", nombresList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("QUERY", "ERRRRROR :c"            );
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
