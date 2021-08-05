package com.example.to_do;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView useridtxt;
    TextView idtxt;
    TextView titleTxt;
    ImageView imageView;
    Toolbar toolbar;
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
        toolbar=findViewById(R.id.detail);
        setSupportActionBar(toolbar);



        Glide.with(this)
                .load( imgUrl)
                .into(imageView);

        useridtxt.setText("User id :"+userId);
        idtxt.setText("id :"+id);
        titleTxt.setText("title :"+title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.back){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}