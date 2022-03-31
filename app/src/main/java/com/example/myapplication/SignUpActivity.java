package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Toast errorInsertUser;

    EditText userName;
    EditText password;
    EditText height;
    EditText weight;

    Button nextPageSigning;

    Intent enterPage;

    DBHelper addUser;
    DBUserCalc addUserCalc;
    DBFoodTable addFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        makeDBHelper();
        makeIntent();
        makeErrorToast();
        connectById();

    }

    public void makeErrorToast(){
        errorInsertUser = new Toast(this);
    }
    public void connectById(){
        nextPageSigning = findViewById(R.id.signUpButton);
        userName = findViewById(R.id.userNameSign);
        password = findViewById(R.id.passwordSign);
        height = findViewById(R.id.heighSign);
        weight = findViewById(R.id.weightSign);
    }
    public void makeDBHelper(){
        addUser = new DBHelper(this);
        addUserCalc = new DBUserCalc(this);
        addFoods = new DBFoodTable(this);
    }
    public void makeIntent(){
        enterPage = new Intent(this, MainActivity.class);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signUpNextPage(View view) {
        String userNameText = userName.getText().toString();
        String passwordText = password.getText().toString();
        int heightNumber =0;
        int weightNumber=0;
        try {
            heightNumber = Integer.parseInt(height.getText().toString());
            weightNumber = Integer.parseInt(weight.getText().toString());
            long userId = addUser.insert(userNameText,passwordText,heightNumber,weightNumber);
            Toast.makeText(this,String.valueOf(userId),Toast.LENGTH_LONG).show();
            if(userId == -1){
                Toast.makeText(SignUpActivity.this,"This username is already in use",Toast.LENGTH_LONG).show();
            }
            else if(userId ==0){
                Toast.makeText(SignUpActivity.this,"There is any user that have this name",Toast.LENGTH_LONG).show();
            }
            else {
                addUserCalc.insert(userId);
                Food [] foods = {
                        new Food("Bread (100g)",270,49,9,3,((BitmapDrawable)getResources().getDrawable(R.drawable.bread)).getBitmap()),
                        new Food("Chicken Breast (100g)",160,0,30,4,((BitmapDrawable)getResources().getDrawable(R.drawable.chickenbreast)).getBitmap()),
                        new Food("Rice (100g)",130,28,3,0,((BitmapDrawable)getResources().getDrawable(R.drawable.rice)).getBitmap())
                };
                for(int i=0;i<foods.length;i++){
                    addFoods.insert(userId,foods[i]);
                }
                startActivity(enterPage);
            }
        }
        catch (NumberFormatException e){
            Toast.makeText(SignUpActivity.this,"Your height and your weight must be numbers",Toast.LENGTH_LONG).show();
        }

    }
}