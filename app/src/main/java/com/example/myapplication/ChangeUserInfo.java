package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChangeUserInfo extends AppCompatActivity {

    EditText newPass, newHeight, newWeight;
    long userId;
    DBHelper changeUser;
    Intent backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        init();
    }

    public void init(){
        newPass = findViewById(R.id.newPassword);
        newHeight = findViewById(R.id.newHeight);
        newWeight = findViewById(R.id.newWeight);

        Intent getUserId = getIntent();
        userId = getUserId.getLongExtra("id",0);

        changeUser = new DBHelper(this);

    }

    public void changeWeight(View view) {
        int newWeightInt =  Integer.parseInt(newWeight.getText().toString());
        changeUser.changeWeight(newWeightInt,userId);
    }

    public void changeHeight(View view) {
        int newHeightInt =  Integer.parseInt(newHeight.getText().toString());
        changeUser.changeHeight(newHeightInt,userId);
    }

    public void changePass(View view) {
        String newPassString =  newPass.getText().toString();
        changeUser.changePass(newPassString,userId);
    }

    public void backToMain(View view) {
        backToMain = new Intent(this,MainPageActivity.class);
        backToMain.putExtra("id",userId);
        startActivity(backToMain);
    }
}