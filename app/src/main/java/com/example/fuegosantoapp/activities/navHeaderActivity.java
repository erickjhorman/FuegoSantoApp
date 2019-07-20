package com.example.fuegosantoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuegosantoapp.R;

public class navHeaderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        buttonlogin = (Button) findViewById(R.id.buttonlogin);
        buttonlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonlogin)
            startActivity(new Intent(this, loginActivity.class));
    }



}
