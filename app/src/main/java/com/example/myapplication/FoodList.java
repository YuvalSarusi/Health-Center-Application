package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    Intent moveNext;
    long userId;

    ListView listView;
    ArrayList<Food> foodList;
    ArrayList<Bitmap>  imagesList;

    Food chosenFood;

    ArrayList<String> textList;
    ArrayAdapter<String> textAdapter;

    DBFoodTable dbFoodHelper;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        init();
    }

    public void init(){
        Intent getUserId = getIntent();
        userId = getUserId.getLongExtra("id",0);

        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.foodList);
        Intent getIdFromMain = getIntent();
        userId = getIdFromMain.getLongExtra("id",0);
        dbFoodHelper = new DBFoodTable(this);
        foodList = new ArrayList<>();
        foodList = dbFoodHelper.selectAll(userId);
        textList = new ArrayList<String>();//array for text only
        imagesList = new ArrayList<Bitmap>();//array for pictures


        for(int i=0; i<foodList.size(); i++) {
            String currentFruit = foodList.get(i).getNameOfFood()+", Cal: "+foodList.get(i).getSumOfCal();
            textList.add(currentFruit);
            imagesList.add(foodList.get(i).getPhoto());
        }

        textAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textList);
        listView.setAdapter(textAdapter);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }
    private void moveFood(Intent intent, Food food){
        intent.putExtra("foodName",food.getNameOfFood());
        intent.putExtra("foodCal",food.getSumOfCal());
        intent.putExtra("foodCar",food.getSumOfCar());
        intent.putExtra("foodFat",food.getSumOfFat());
        intent.putExtra("foodPro",food.getSumOfPro());
        intent.putExtra("foodPhoto",food.getPhoto());
    }

    public void addFood(View view) {
        //add new food to the user's list
        moveNext = new Intent(this, addNewFood.class);
        moveNext.putExtra("id",userId);
        startActivity(moveNext);
   }

    public void backToMain(View view) {
        //send the user to the main page
        moveNext = new Intent(this,MainPageActivity.class);
        moveNext.putExtra("id",userId);
        startActivity(moveNext);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //set the food photo on the screen
        imageView.setImageBitmap(imagesList.get(position));
        chosenFood = new Food(foodList.get(position));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //let the user delete a food from the list
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Removing Food From List");
        builder.setMessage("Are you sure you want to remove this book?");
        builder.setPositiveButton("Yes, Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbFoodHelper.deleteById(position);
                        textAdapter.remove(textAdapter.getItem(position));
                        textAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }
}