package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.to_do.Adapter.ToDoAdapter;
import com.example.to_do.Model.ToDoData;
import com.example.to_do.Service.ToDoApi;
import com.example.to_do.Service.TodoService;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements ToDoAdapter.OnItemClickListener {
    public static final String url="imageUrl";
    public static final String userId="userId";
    public static final String id="id";
    public static final String title="title";
    RecyclerView recyclerView;

    ToDoAdapter adapter;
    TextView user_name;
    ImageView user_profile_Image;
    List<ToDoData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_name=findViewById(R.id.user_name_txt);
        user_profile_Image=findViewById(R.id.user_imageView);
        list=TodoService.get().getList();
        recyclerView=findViewById(R.id.recyView);
        recyclerView.setHasFixedSize(true);
        if(AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }
        else{
            loadUserProfile(AccessToken.getCurrentAccessToken());
            callToDo();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadUserProfile(AccessToken currentAccessToken) {
        GraphRequest request=GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name=object.getString("name");
                    user_name.setText(name);
                    String img=object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Glide.with(getApplicationContext())
                            .load(img)
                            .fitCenter()
                            .centerInside()
                            .into(user_profile_Image);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle=new Bundle();
        bundle.putString("fields","name,picture.width(150).height(150)");
        request.setParameters(bundle);
        request.executeAsync();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void goLoginScreen() {
        Intent intent=new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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



    void callToDo(){
        ToDoApi todoapi=TodoService.get().getJsonUrlRetrofit().create(ToDoApi.class);
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

    public void logoutButton(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

}