package com.example.ecreyes.ccompra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ecreyes.ccompra.Objetos.FirebaseReferences;
import com.example.ecreyes.ccompra.Objetos.Tienda;
import com.example.ecreyes.ccompra.Objetos.myCallBack;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgregarTiendaProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgregarTiendaProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarTiendaProfileFragment extends Fragment implements  View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String TAG_TIENDA = "TIENDA";
    View v;
    DatabaseReference tiendaRef;
    FirebaseDatabase database;
    StorageReference mStorage;

    //Código para campo de imagen, boton para subir y la imagen a mostrar después.
    Button btn_subir;
    ImageView mImageView;
    static final int GALLERY_INTENT = 1;
    //bara de progreso de carga para la imagen
    ProgressDialog mProgressDialog;

    //variables para el formulario
    Button addTienda;
    Uri downloadUri;
    EditText ntienda;
    EditText ndescripcion;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AgregarTiendaProfileFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarTiendaProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarTiendaProfileFragment newInstance(String param1, String param2) {
        AgregarTiendaProfileFragment fragment = new AgregarTiendaProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //conexión a storage
        mStorage = FirebaseStorage.getInstance().getReference();
        //conexión a bd y tabla tienda
        database = FirebaseDatabase.getInstance();
        tiendaRef = database.getReference(FirebaseReferences.TIENDA_REFERENCES);
        mProgressDialog = new ProgressDialog(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_agregar_tienda, container, false);
        btn_subir = v.findViewById(R.id.btn_subir);
        mImageView = v.findViewById(R.id.fimg);
        addTienda = v.findViewById(R.id.btnTienda);
        ntienda = v.findViewById(R.id.nombreTienda);
        ndescripcion = v.findViewById(R.id.descripcionTienda);
        btn_subir.setOnClickListener(this);
        addTienda.setOnClickListener(this);
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
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        alert.setTitle("Tienda agregada satisfactoriamente");
        switch (v.getId()){
            case R.id.btn_subir:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
                break;
            case R.id.btnTienda:
                Log.d(TAG_TIENDA,"boton presionado");
                Log.d(TAG_TIENDA,downloadUri+"");
                readDataTienda(new myCallBack() {
                    @Override
                    public void onCallback(int value) {
                        tiendaRef.child("idauto").setValue(value+1);
                        Tienda tienda = new Tienda(value+1,true,ntienda.getText().toString(),ndescripcion.getText().toString(),downloadUri+"",user.getEmail());
                        tiendaRef.push().setValue(tienda);
                        ntienda.setText("");
                        ndescripcion.setText("");
                    }
                });
            default:
                break;
        }
        alert.create().show();
    }

    //método se ejecuta una vez que se carga la imagen.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode== Activity.RESULT_OK){
            mProgressDialog.setTitle("Subiendo...");
            mProgressDialog.setMessage("subiendo foto a firebase");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());

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
                        downloadUri = task.getResult();
                        Log.d(TAG_TIENDA,downloadUri+"");
                        Glide.with(AgregarTiendaProfileFragment.this)
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
    public void readDataTienda(myCallBack myCallback){
        tiendaRef.child("idauto").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
