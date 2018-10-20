package com.example.ecreyes.ccompra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;
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
 * {@link AgregarTiendaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgregarTiendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarTiendaFragment extends Fragment implements View.OnClickListener{
    DatabaseReference categoriaRef;
    FirebaseDatabase database;
    StorageReference mStorage;
    ProgressDialog mProgressDialog;
    View v;
    Button btn_subir;
    ImageView mImageView;
    static final int GALLERY_INTENT = 1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AgregarTiendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarTiendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarTiendaFragment newInstance(String param1, String param2) {
        AgregarTiendaFragment fragment = new AgregarTiendaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        categoriaRef = database.getReference(FirebaseReferences.CATEGORIA_REFERENCES);
        mProgressDialog = new ProgressDialog(getContext());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_agregar_tienda, container, false);
        btn_subir = (Button) v.findViewById(R.id.btn_subir);
        mImageView = (ImageView) v.findViewById(R.id.fimg);
        btn_subir.setOnClickListener(this);


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
            case R.id.btn_subir:
                Toast.makeText(getActivity(),"Holaaaaa",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

                break;
                default:
                    break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode== Activity.RESULT_OK){
            mProgressDialog.setTitle("Subiendo...");
            mProgressDialog.setMessage("subiendo foto a firebase");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            Uri uri = data.getData();
            //Log.d(TAG,uri.getLastPathSegment()+"");
            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            /*
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MainActivity.this,"Imagen subida correctamente", Toast.LENGTH_SHORT).show();
                }
            });
            */

            filePath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        mProgressDialog.dismiss();
                        Uri downloadUri = task.getResult();
                        Log.d(TAG,downloadUri+"");
                        Glide.with(AgregarTiendaFragment.this)
                                .load(downloadUri)
                                .fitCenter()
                                .centerCrop()
                                .into(mImageView);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
