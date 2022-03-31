package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CheckRealUser extends AppCompatActivity {

    Intent moveFromPage;
    DBHelper checkUserData;
    EditText userName, userPass;
    Toast loginStatus;
    int toastTime;
    long userId =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_real_user);
        init();
    }


    public void init(){
        checkUserData = new DBHelper(this);
        userName = findViewById(R.id.userName);
        userPass = findViewById(R.id.password);
        loginStatus = new Toast(this);
        toastTime = loginStatus.LENGTH_SHORT;
        Intent getUserId = getIntent();
        userId = getUserId.getLongExtra("id",0);
    }

    public void backToMain(View view) {
        moveFromPage = new Intent(this,MainPageActivity.class);
        startActivity(moveFromPage);
    }

    public void checkUser(View view) {
        int userIdCheck = checkUserData.checkUserLogin(userName,userPass);
        if(userId > 0){
            moveFromPage = new Intent(this,ChangeUserInfo.class);
            moveFromPage.putExtra("id",userId);
            startActivity(moveFromPage);
        }
        else if (userId == 0){
            loginStatus.makeText(this,"There Is No Username Like You Inserted. Please Enter Username Again",toastTime).show();
        }
        else{
            loginStatus.makeText(this,"Your Username Doesn't Mach To Your Password. Check your Username And Password Again",toastTime).show();
        }
    }
}