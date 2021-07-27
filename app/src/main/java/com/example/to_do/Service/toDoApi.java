package com.example.to_do.Service;

import com.example.to_do.Model.toDoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface toDoApi {
    @GET("todos/")
    Call<List<toDoData>> getData();
}
