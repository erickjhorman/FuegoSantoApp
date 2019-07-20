package com.example.fuegosantoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fuegosantoapp.R;
import com.example.fuegosantoapp.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewUserCorreo;
    private ImageView imageViewUserAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }


        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserCorreo = (TextView) findViewById(R.id.textViewUserCorreo);
        imageViewUserAvatar = (ImageView) findViewById(R.id.imageViewUserAvatar);

        textViewUserCorreo.setText(SharedPrefManager.getInstance(this).getUserEmail());
    }



}
