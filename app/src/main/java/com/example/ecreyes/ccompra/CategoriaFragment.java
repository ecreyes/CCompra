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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriaFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View v;
    Button btn_categoria;
    EditText et_categoria;
    FirebaseDatabase database;
    DatabaseReference categoriaRef;
    StorageReference mStorage;
    ProgressDialog mProgressDialog;

    public final static  String LOGTAG = "HolaLogs";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriaFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        mStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        categoriaRef = database.getReference(FirebaseReferences.CATEGORIA_REFERENCES);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_categoria, container, false);
        btn_categoria = (Button)  v.findViewById(R.id.btn_categoria);
        et_categoria = (EditText) v.findViewById(R.id.et_categoria);
        btn_categoria.setOnClickListener(this);
        et_categoria.setOnClickListener(this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_categoria:
                if ((et_categoria.getText().toString()).equals("")) {
                    Toast.makeText(getActivity(),"Categoria No Agregada",Toast.LENGTH_LONG).show();
                }
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
                break;
            case R.id.et_categoria:

                default:
                    break;
        }
        }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
