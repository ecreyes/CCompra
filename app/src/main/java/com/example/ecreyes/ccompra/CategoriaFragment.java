package com.example.ecreyes.ccompra;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecreyes.ccompra.Objetos.Categoria;
import com.example.ecreyes.ccompra.Objetos.FirebaseReferences;
import com.example.ecreyes.ccompra.Objetos.myCallBack;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import static android.content.ContentValues.TAG;

public class CategoriaFragment extends Fragment implements View.OnClickListener{
    //Se declara la view
    View v;
    //campos utilizados en el layout
    Button btn_categoria;
    EditText et_categoria;
    //referencias a la base de datos.
    FirebaseDatabase database;
    DatabaseReference categoriaRef;

    //generado automaticamente.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    //constructor vacío
    public CategoriaFragment() {
    }


    //método generado automaticamente.
    public static CategoriaFragment newInstance(String param1, String param2) {

        CategoriaFragment fragment = new CategoriaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //se conecta a la BD y obtiene la referencia a la tabla categoria.
        database = FirebaseDatabase.getInstance();
        categoriaRef = database.getReference(FirebaseReferences.CATEGORIA_REFERENCES);

        //generado automaticamente
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //método de Callback para el idauto de categoría
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //se asocia la view con el layout del fragmento.
        v = inflater.inflate(R.layout.fragment_categoria, container, false);
        //se asignan los id a los campos
        btn_categoria = v.findViewById(R.id.btn_categoria);
        et_categoria = v.findViewById(R.id.et_categoria);
        btn_categoria.setOnClickListener(this);
        return v;
    }

    //generado automaticamente.
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //generado automaticamente.
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

    //generado automaticamente
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_categoria:
                if ((et_categoria.getText().toString()).equals("")) {
                    Toast.makeText(getActivity(),"Categoria No Agregada",Toast.LENGTH_LONG).show();
                }else{
                    readData(new myCallBack() {
                        @Override
                        public void onCallback(int value) {
                            categoriaRef.child("idauto").setValue(value+1);
                            Categoria cat = new Categoria(value+1,et_categoria.getText().toString());
                            categoriaRef.push().setValue(cat);
                            et_categoria.setText("");
                            Toast.makeText(getActivity(),"Categoría Agregada con Éxito",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
    }

    //generado automaticamente.
    interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
