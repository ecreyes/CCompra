package com.example.ecreyes.ccompra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    FirebaseDatabase database;
    DatabaseReference categoriaRef;
    Button botonCategoria;
    EditText etCategoria;

    //IMAGEN
    Button mUploadButton;
    StorageReference mStorage;
    static final int GALLERY_INTENT = 1;
    ImageView mImageView;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonCategoria = findViewById(R.id.btn_categoria);
        etCategoria = findViewById(R.id.et_categoria);
        mUploadButton = findViewById(R.id.btn_subir);
        mImageView = findViewById(R.id.fimg);
        mProgressDialog = new ProgressDialog(this);

        mStorage = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance();
        categoriaRef = database.getReference(FirebaseReferences.CATEGORIA_REFERENCES);
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

    public void pressCategoria(View view) {
        readData(new myCallBack() {
            @Override
            public void onCallback(int value) {
                categoriaRef.child("idauto").setValue(value+1);
                Categoria cat = new Categoria(value+1,etCategoria.getText().toString());
                categoriaRef.push().setValue(cat);
                etCategoria.setText("");
            }
        });
    }

    public void abrirGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
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
                        Glide.with(MainActivity.this)
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
}
