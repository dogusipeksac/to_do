package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TextView user_Name;
    ImageView user_Image;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user_Name=findViewById(R.id.profileName);
        user_Image=findViewById(R.id.profileImage);
        toolbar=findViewById(R.id.profile);
        setSupportActionBar(toolbar);

        loadUserProfile(AccessToken.getCurrentAccessToken());
    }



    private void loadUserProfile(AccessToken currentAccessToken) {
        GraphRequest request=GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name=object.getString("name");
                    user_Name.setText(name);
                    String img=object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Glide.with(getApplicationContext())
                            .load(img)
                            .fitCenter()
                            .centerInside()
                            .into(user_Image);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.back){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

}