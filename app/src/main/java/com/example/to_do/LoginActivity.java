package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.to_do.Dialog.LoadingDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        loginButton=findViewById(R.id.login_button);
        dialog=new LoadingDialog(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();

            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Canceled login.", Toast.LENGTH_SHORT).show();
            }   

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error:"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goMainScreen() {
        Intent intent=new Intent(this,MainActivity.class);
        dialog.startLoadingDialog();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismissDialog();
            }
        },3000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



}