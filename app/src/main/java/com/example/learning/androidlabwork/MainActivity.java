package com.example.learning.androidlabwork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.learning.androidlabwork.Model.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    RecyclerView postList;
    DatabaseReference myRef;
    static Context context;
    Button addPost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postList = (RecyclerView) findViewById(R.id.post_list);
        addPost = (Button) findViewById(R.id.addPost);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(this));
        myRef = FirebaseDatabase.getInstance().getReference("posts");
        context = this;

        addPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
            }
        });

    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        StorageReference mStorageRef;
        View mView;
        public PostViewHolder(View itemView){
            super(itemView);

            mView = itemView;

        }

        public  void setTitle(String title){
            TextView postTitle = (TextView) mView.findViewById(R.id.post_title);
            postTitle.setText(title);
        }

        public  void setDescription(String desc){
            TextView postTitle = (TextView) mView.findViewById(R.id.post_description);
            postTitle.setText(desc);
        }

        public void setImage(String image){
            ImageView postImage = (ImageView) mView.findViewById(R.id.post_image);
            mStorageRef = FirebaseStorage.getInstance().getReference().child(image);
            Glide.with(context).using(new FirebaseImageLoader())
                    .load(mStorageRef)
                    .into(postImage);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Post,PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(
                Post.class,
                R.layout.post_row,
                PostViewHolder.class,
                myRef
        ) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(model.getImageId());
            }
        };
        postList.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
