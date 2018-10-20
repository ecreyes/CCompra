package com.example.ecreyes.ccompra;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


public class ListaTiendasFragment extends Fragment {
    //Se declara la view
    View v;
    //variables
    ListView mylist;
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
        MyCustomListAdapter myadapter = new MyCustomListAdapter(getContext(),image, text);
        mylist.setAdapter((ListAdapter) myadapter);
        return v;
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
