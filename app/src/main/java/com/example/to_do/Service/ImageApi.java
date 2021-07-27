package com.example.to_do.Service;

import com.example.to_do.Model.ImageData;
import com.example.to_do.Model.toDoData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageApi {
    //https://picsum.photos/200
    @GET("200/")
    Call<ImageData> getDatas();
}
