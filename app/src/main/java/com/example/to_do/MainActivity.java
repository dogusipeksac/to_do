package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.to_do.Adapter.ToDoAdapter;
import com.example.to_do.Model.ToDoData;
import com.example.to_do.Service.ToDoApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ToDoAdapter.OnItemClickListener {
    public static final String url="imageUrl";
    public static final String userId="userId";
    public static final String id="id";
    public static final String title="title";
    RecyclerView recyclerView;
    List<ToDoData> list;
    ToDoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyView);
        recyclerView.setHasFixedSize(true);

        list=new ArrayList<>();



        callToDo();

    }

    private void PutDataIntoRecyView(List<ToDoData> list) {

        adapter=new ToDoAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);

    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailActivity.class);
        ToDoData clickedItem =list.get(position);
        detailIntent.putExtra(url,clickedItem.getImgUrl());
        detailIntent.putExtra(userId,clickedItem.getUserId());
        detailIntent.putExtra(id,clickedItem.getId());
        detailIntent.putExtra(title,clickedItem.getTitle());
        startActivity(detailIntent);
    }


    Retrofit getJsonUrlRetrofit(String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    void callToDo(){
        ToDoApi todoapi=getJsonUrlRetrofit("https://jsonplaceholder.typicode.com/").create(ToDoApi.class);
        Call<List<ToDoData>> call=todoapi.getData();
        call.enqueue(new Callback<List<ToDoData>>() {
            @Override
            public void onResponse(Call<List<ToDoData>> call, Response<List<ToDoData>> response) {
                if (response.code()!=200){
                    //handle the error display
                    Toast.makeText(MainActivity.this, "Error:"+response.message(), Toast.LENGTH_LONG).show();
                    return;
                }
                List<ToDoData> data=response.body();
                for(ToDoData tododata : data ){
                    list.add(tododata);
                }

                PutDataIntoRecyView(list);
            }

            @Override
            public void onFailure(Call<List<ToDoData>> call, Throwable t) {

            }
        });
    }

}