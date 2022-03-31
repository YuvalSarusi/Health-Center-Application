package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExplainActivity extends AppCompatActivity {

    long userId;
    Intent backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        init();
    }

    public void init(){
        Intent getUserId = getIntent();
        userId = getUserId.getLongExtra("id",0);
        backToMain = new Intent(this,MainPageActivity.class);
    }

    public void backToMain(View view) {
        backToMain.putExtra("id",userId);
        startActivity(backToMain);
    }
}