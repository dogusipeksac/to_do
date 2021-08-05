package com.example.to_do.Service;

import android.content.Context;
import android.widget.Toast;

import com.example.to_do.MainActivity;
import com.example.to_do.Model.ToDoData;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoService {

   String url="https://jsonplaceholder.typicode.com/";
    List<ToDoData> list;


    private TodoService() {
        list=new ArrayList<ToDoData>();
    }


    private static TodoService myTodoService;


    public static TodoService get(){
        if (myTodoService==null){
            myTodoService=new TodoService();
        }
        return myTodoService;
    }
    public List<ToDoData> getList(){
        return  list;
    }
    public Retrofit getJsonUrlRetrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
