package com.example.to_do;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.to_do.Adapter.ToDoAdapter;
import com.example.to_do.Dialog.LoadingDialog;
import com.example.to_do.Model.ToDoData;
import com.example.to_do.Service.ToDoApi;
import com.example.to_do.Service.TodoService;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


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
    List<ToDoData> list;
    LoadingDialog dialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=TodoService.get().getList();
        dialog=new LoadingDialog(this);
        recyclerView=findViewById(R.id.recyView);
        recyclerView.setHasFixedSize(true);
        toolbar=findViewById(R.id.main);
        setSupportActionBar(toolbar);

        if(AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }
        else{
            callToDo();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void logout() {
            dialog.startLoadingDialog();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
            @Override
            public void run()
                {
                    LoginManager.getInstance().logOut();
                    goLoginScreen();
                    dialog.dismissDialog();
                }
           },2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id=item.getItemId();
       if(id==R.id.menu_profile){
            Intent intent=new Intent(this,ProfileActivity.class);
            startActivity(intent);
       }
       if(id==R.id.menu_exit){
           AlertDialog.Builder builder=new AlertDialog.Builder(this);
           builder.setTitle("Are you sure to exit ?")
                   .setMessage("Click yes to confirm")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
               }
           });
           AlertDialog alertDialog=builder.create();
           alertDialog.show();



        }
       return true;
    }
}