package com.example.to_do.Service;

import com.example.to_do.Model.ToDoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ToDoApi {
    @GET("todos/")
    Call<List<ToDoData>> getData();
}
