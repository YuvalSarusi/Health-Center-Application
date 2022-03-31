package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class addNewFood extends AppCompatActivity {

    EditText foodName, foodCal, foodCar, foodPro, foodFat;
    ImageView foodPhoto;
    Bitmap foodPicture;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CHECKING_FOOD_VALUE = -1;
    String foodNameText;
    int foodCalNum, foodCarNum, foodProNum, foodFatNum;
    Intent backToList;

    long userId;
    long foodId;
    DBFoodTable dbFoodHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food2);
        setObj();
        setVal();
    }

    public void setObj(){
        Intent getUserId = getIntent();
        userId = getUserId.getLongExtra("id",0);
        backToList = new Intent(this,FoodList.class);
        foodName = findViewById(R.id.foodName);
        foodCal = findViewById(R.id.calId);
        foodCar = findViewById(R.id.carId);
        foodPro = findViewById(R.id.proId);
        foodFat = findViewById(R.id.fatId);
        foodPhoto = findViewById(R.id.foodPhoto);
        foodPhoto.setImageResource(R.drawable.food1);
        dbFoodHelper = new DBFoodTable(this);
        foodPicture = BitmapFactory. decodeResource(getResources(), R. drawable.food1);

        foodNameText = null;
        foodCalNum = CHECKING_FOOD_VALUE;
        foodCarNum = CHECKING_FOOD_VALUE;
        foodFatNum = CHECKING_FOOD_VALUE;
        foodProNum = CHECKING_FOOD_VALUE;
    }

    public void setVal(){
        foodNameText = foodName.getText().toString();
        try {
            foodCalNum = Integer.parseInt(foodCal.getText().toString());
            foodCarNum = Integer.parseInt(foodCar.getText().toString());
            foodProNum = Integer.parseInt(foodPro.getText().toString());
            foodFatNum = Integer.parseInt(foodFat.getText().toString());
        }
        catch (NumberFormatException exception){
            Toast.makeText(this,"Enter Positive Values In: Cal, Car, Fat and Pro",Toast.LENGTH_LONG);
        }
    }

    public void addFoodFunc(View view) {
        setVal();
        Food food;
        Bitmap image = ((BitmapDrawable) foodPhoto.getDrawable()).getBitmap();
        if (foodCalNum <= CHECKING_FOOD_VALUE ||
            foodCarNum <= CHECKING_FOOD_VALUE ||
            foodFatNum <= CHECKING_FOOD_VALUE ||
            foodProNum <= CHECKING_FOOD_VALUE
        ){
            Toast.makeText(this,"Please Enter Positive Values In All Of The States",Toast.LENGTH_LONG);
        }
        else{
            food = new Food(foodNameText,foodCalNum,foodCarNum,foodProNum,foodFatNum,image);
            foodId = dbFoodHelper.insert(userId,food);
            if(foodId > 0){
                backToList.putExtra("id",userId);
                startActivity(backToList);
            }
        }
    }

    public void addPhoto(View view) {
        Intent shootPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(shootPic.resolveActivity(getPackageManager()) != null)
            startActivityForResult(shootPic,REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_IMAGE_CAPTURE == requestCode && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bit = (Bitmap) extras.get("data");
            foodPhoto.setImageBitmap(bit);
        }

    }

    public void backToMain(View view) {
        Intent backToMain = new Intent(this,MainPageActivity.class);
        backToMain.putExtra("id",userId);
        startActivity(backToMain);
    }
}