package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.to_do.Adapter.toDoAdapter;
import com.example.to_do.Model.toDoData;
import com.example.to_do.Service.toDoApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements toDoAdapter.OnItemClickListener {
    public static final String url="imageUrl";
    public static final String userId="userId";
    public static final String id="id";
    public static final String title="title";
    RecyclerView recyclerView;
    List<toDoData> list;
    toDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyView);
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        toDoApi todoapi=retrofit.create(toDoApi.class);
        Call<List<toDoData>> call=todoapi.getData();
        call.enqueue(new Callback<List<toDoData>>() {
            @Override
            public void onResponse(Call<List<toDoData>> call, Response<List<toDoData>> response) {
                if (response.code()!=200){
                    //handle the error display
                    return;
                }
                List<toDoData> data=response.body();
                for(toDoData tododata : data ){

                    list.add(tododata);
                }

                PutDataIntoRecyView(list);
            }

            @Override
            public void onFailure(Call<List<toDoData>> call, Throwable t) {

            }
        });

    }

    private void PutDataIntoRecyView(List<toDoData> list) {

        adapter=new toDoAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);

    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailActivity.class);
        toDoData clickedItem =list.get(position);
        detailIntent.putExtra(url,clickedItem.getImgUrl());
        detailIntent.putExtra(userId,clickedItem.getUserId());
        detailIntent.putExtra(id,clickedItem.getId());
        detailIntent.putExtra(title,clickedItem.getTitle());
        startActivity(detailIntent);
    }
}