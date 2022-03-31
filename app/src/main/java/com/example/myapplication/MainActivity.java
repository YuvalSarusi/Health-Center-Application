package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.time.LocalDateTime ;


public class MainActivity extends AppCompatActivity {

    int toastTime;
    Toast logginStatus;
    Button signIn, logIn;
    Intent mainPage, signInPage;
    EditText userNameText, passwordText;
    DBHelper findUserName;
    DBUserCalc setUserDay;
    DBFoodTable foodTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectItemById();
        makeDBHelper();
        makeIntent();
    }

    public void connectItemById(){
        signIn = findViewById(R.id.signUp);
        logIn = findViewById(R.id.logIn);
        userNameText = findViewById(R.id.userName);
        passwordText = findViewById(R.id.password);
    }
    public void makeLogginStartToast(){
        logginStatus = new Toast(this);
        toastTime = logginStatus.LENGTH_SHORT;
    }
    public void makeDBHelper(){
        findUserName = new DBHelper(this);
        foodTable = new DBFoodTable(this);
        setUserDay = new DBUserCalc(this);
    }
    public void makeIntent(){
        signInPage = new Intent(this, SignUpActivity.class);
    }

    public void signUpNow(View view) {
        startActivity(signInPage);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void logInNow(View view) {
        long userId =0;
        userId = findUserName.checkUserLogin(userNameText,passwordText);
        makeLogginStartToast();
        if(userId == 0){
            logginStatus.makeText(this,"There Is No Username Like You Inserted. Please Enter Username Again",toastTime).show();
        }
        else if (userId == -1){
            logginStatus.makeText(this,"Your Username Doesn't Mach To Your Password. Check your Username And Password Again",toastTime).show();
        }
        else{
            Toast.makeText(this,"logInNow", Toast.LENGTH_SHORT).show();
            mainPage = new Intent(this, MainPageActivity.class);
            mainPage.putExtra("id",userId);
            LocalDateTime now = LocalDateTime.now();
            int nowDay = now.getDayOfMonth();
            if(nowDay != setUserDay.getLastConnectDay(userId)){
                setUserDay.resetUserCalc(userId,nowDay);
            }
            startActivity(mainPage);
        }
    }


    public void deleteSql(View view) {
        DBHelper d1 = new DBHelper(this);
        d1.deleteTable();
        DBUserCalc d3 = new DBUserCalc(this);
        d3.deleteTable();
        DBFoodTable d2 = new DBFoodTable(this);
        d2.deleteTable();

    }
}