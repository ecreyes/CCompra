package com.example.ecreyes.ccompra;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecreyes.ccompra.Objetos.Categoria;
import com.example.ecreyes.ccompra.Objetos.Tienda;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaTiendasProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaTiendasProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaTiendasProfileFragment extends Fragment {

    View v;
    RecyclerView rv;
    List<Tienda> tiendas;
    ListaTiendasProfileAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaTiendasProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaTiendasProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaTiendasProfileFragment newInstance(String param1, String param2) {
        ListaTiendasProfileFragment fragment = new ListaTiendasProfileFragment();
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
        v = inflater.inflate(R.layout.fragment_lista_tiendas_profile, container, false);
        rv = (RecyclerView) v.findViewById(R.id.recycler_tiendas_profile);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        tiendas = new ArrayList<Tienda>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapter = new ListaTiendasProfileAdapter(tiendas);
        rv.setAdapter(adapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            String email =  user.getEmail();
            Log.e(email,"Holaaaa");
        }
        else{
            Log.e("Mala perca","Very Bad Perch");
        }
        database.getReference("Tienda").
                orderByChild("email").
                equalTo(user.getEmail()).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tiendas.removeAll(tiendas);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()){
                    try{
                        Tienda tienda = snapshot.getValue(Tienda.class);
                        tiendas.add(tienda);

                    }catch (Exception e){
                        Log.e("Error ","Error");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
