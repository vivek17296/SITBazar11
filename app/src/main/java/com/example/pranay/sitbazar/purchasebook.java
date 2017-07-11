package com.example.pranay.sitbazar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

public class purchasebook extends AppCompatActivity {
    private String  mbook_key=null;
    private DatabaseReference mDatabase;
    private ImageView mbooksingleimage;
    private TextView mbooksinglename;
    private TextView mbooksingleauthor;
    private TextView mbooksingleedition;
    private TextView mbooksingleprice;
    private TextView mbooksingledescription;
    private Button pur;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasebook);
        Firebase.setAndroidContext(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sell");

        mAuth = FirebaseAuth.getInstance();

         mbook_key = getIntent().getExtras().getString("book_id");

        mbooksinglename = (TextView) findViewById(R.id.singlename);
        mbooksingleauthor = (TextView) findViewById(R.id.singleauthor);
        mbooksingleedition = (TextView) findViewById(R.id.singleedition);
        mbooksingleprice = (TextView) findViewById(R.id.singleprice);
        mbooksingledescription = (TextView) findViewById(R.id.singledescription);
        mbooksingleimage = (ImageView) findViewById(R.id.singleimage);
        pur = (Button) findViewById(R.id.purchasebtn);


        //Toast.makeText(purchasebook.this, book_key ,Toast.LENGTH_LONG).show();

        mDatabase.child(mbook_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String book_name = (String) dataSnapshot.child("name").getValue();
                String book_author = (String) dataSnapshot.child("author").getValue();
                String book_edition = (String) dataSnapshot.child("edition").getValue();
                String book_price = (String) dataSnapshot.child("price").getValue();
                String book_descripition = (String) dataSnapshot.child("description").getValue();
                String book_image = (String) dataSnapshot.child("image").getValue();
                String book_uid = (String) dataSnapshot.child("usrid").getValue();

                mbooksinglename.setText(book_name);
                mbooksingleauthor.setText(book_author);
                mbooksingleedition.setText(book_edition);
                mbooksingleprice.setText(book_price);
                mbooksingledescription.setText(book_descripition);
                Picasso.with(purchasebook.this).load(book_image).into(mbooksingleimage);

                if(mAuth.getCurrentUser().getUid().equals(book_uid)) {
                }
                else {
                 //   Toast.makeText(purchasebook.this, mAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();
                    pur.setVisibility(View.VISIBLE);
                }
                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(mbook_key).removeValue();
                Toast.makeText(getApplicationContext(), "Purchase successful", Toast.LENGTH_LONG).show();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(purchasebook.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
