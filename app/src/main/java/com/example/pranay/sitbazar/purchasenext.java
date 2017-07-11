package com.example.pranay.sitbazar;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
//import com.firebase.client.core.Context;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;



public class purchasenext extends AppCompatActivity  {

    private RecyclerView mlist;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasenext);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sell");

        mlist = (RecyclerView) findViewById(R.id.m_list);
        mlist.setHasFixedSize(true);
        mlist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<purchase,purchaseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<purchase, purchaseViewHolder>(

                purchase.class,
                R.layout.m_row,
                purchaseViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(purchaseViewHolder viewHolder, purchase model, int position) {
                final String book_key = getRef(position).getKey();
                viewHolder.setName(model.getName());
                viewHolder.setAuthor(model.getAuthor());
                viewHolder.setImage(getApplicationContext() ,model.getImage());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent purchaseintent = new Intent(purchasenext.this , purchasebook.class);
                        purchaseintent.putExtra("book_id",book_key);
                        startActivity(purchaseintent);
                    }
                });

            }
        };

        mlist.setAdapter(firebaseRecyclerAdapter);

    }

    public static class purchaseViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public purchaseViewHolder(View itemView) {
            super(itemView);

            mview=itemView;
        }

        public void setName(String name){
            TextView post_name= (TextView) mview.findViewById(R.id.book_name);
            post_name.setText(name);
        }

        public void setAuthor(String author){
            TextView post_author= (TextView) mview.findViewById(R.id.book_author);
            post_author.setText(author);
        }

       public void setImage(Context ctx, String image){
            ImageView post_image= (ImageView) mview.findViewById(R.id.book_image);
            Picasso.with(ctx).load(image).into(post_image);
        }


    }
}
