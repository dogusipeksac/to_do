package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView useridtxt;
    TextView idtxt;
    TextView titleTxt;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        String imgUrl=intent.getStringExtra("imageUrl");
        String userId=intent.getStringExtra("userId");
        String id=intent.getStringExtra("id");
        String title=intent.getStringExtra("title");

        useridtxt=findViewById(R.id.useridtext);
        idtxt=findViewById(R.id.idTEXT);
        titleTxt=findViewById(R.id.titletext);
        imageView=findViewById(R.id.image_detail_view);


        Glide.with(this)
                .load("https://picsum.photos/200/300")
                .fitCenter()
                .centerInside()
                .into(imageView);

        useridtxt.setText("User id :"+userId);
        idtxt.setText("id :"+id);
        titleTxt.setText("title :"+title);


    }
}