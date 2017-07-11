


package com.example.pranay.sitbazar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnSuccessListener;


public class HomePageActivity2 extends AppCompatActivity {
     DatabaseReference mDatabase;
    private EditText bname;
    private EditText author;
    private EditText edition;
    private EditText eprice;
    private EditText desc;
    private Button register;
    FirebaseDatabase firebaseDatabase;
    private ImageButton mselectimage;
    private Uri imageuri = null;
    private StorageReference mStorage;
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sell");
        mselectimage = (ImageButton) findViewById(R.id.img);
        bname = (EditText) findViewById(R.id.booknamedes);
        author = (EditText) findViewById(R.id.authordes);
        edition = (EditText) findViewById(R.id.editiondes);
        eprice = (EditText) findViewById(R.id.estpricedes);
        desc = (EditText) findViewById(R.id.bookdescriptiondes);
        register = (Button)findViewById(R.id.next);

        mselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallerintent = new Intent(Intent.ACTION_GET_CONTENT);
                gallerintent.setType("image/*");
                startActivityForResult(gallerintent,GALLERY_REQUEST);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String book_name = bname.getText().toString();
                final String author_val = author.getText().toString();
                final String edition_e = edition.getText().toString();
                final String e_price = eprice.getText().toString();
                final String desc_c = desc.getText().toString();


                StorageReference filepath = mStorage.child("BlogImages").child(imageuri.getLastPathSegment());
                filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloaduri = taskSnapshot.getDownloadUrl();

                        DatabaseReference newPost = mDatabase.push();

                        newPost.child("name").setValue(book_name);
                        newPost.child("author").setValue(author_val);
                        newPost.child("edition").setValue(edition_e);
                        newPost.child("price").setValue(e_price);
                        newPost.child("description").setValue(desc_c);



                        newPost.child("usrid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        newPost.child("image").setValue(downloaduri.toString());


                        Toast.makeText(getApplicationContext(), "Upload done", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(HomePageActivity2.this, HomePageActivity.class));

                    }

        });
        }
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK);
        imageuri = data.getData();
        mselectimage.setImageURI(imageuri);


       /* StorageReference filepath = mStorage.child("Photos").child(imageuri.getLastPathSegment());
        filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Fertilizer_Add.this,"Upload done",Toast.LENGTH_LONG).show();
            }
        });*/
    }
}
