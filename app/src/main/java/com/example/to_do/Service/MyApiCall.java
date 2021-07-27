package com.example.to_do.Service;

import com.example.to_do.Model.DataModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiCall {

    //https://jsonplaceholder.typicode.com/todos/   1
    @GET("/todos/1")
    Call<DataModel> getData();

}
