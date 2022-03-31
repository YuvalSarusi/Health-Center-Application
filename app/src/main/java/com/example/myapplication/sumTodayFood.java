package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class sumTodayFood extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DBFoodTable getListOfFood;

    ArrayList<Food> arrayFoodList;
    ListView listView;
    ArrayAdapter<Food> adapter;
    Intent back;
    long userId =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_today_food);
        init();
    }

    public void init(){
        getListOfFood = new DBFoodTable(this);

        arrayFoodList = new ArrayList<>();
        if (!getListOfFood.selectAll(userId).isEmpty())
            arrayFoodList = getListOfFood.selectAll(userId);
        listView = findViewById(R.id.foodList);
        adapter = new ArrayAdapter<Food>(this, android.R.layout.simple_list_item_1, arrayFoodList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Intent getUserId = getIntent();
        userId = getUserId.getLongExtra("id",0);
    }

    private void moveFood(Intent intent, Food food){
        intent.putExtra("foodName",food.getNameOfFood());
        intent.putExtra("foodCal",food.getSumOfCal());
        intent.putExtra("foodCar",food.getSumOfCar());
        intent.putExtra("foodFat",food.getSumOfFat());
        intent.putExtra("foodPro",food.getSumOfPro());
        intent.putExtra("foodPhoto",food.getPhoto());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        moveFood(back,arrayFoodList.get(position));
        back.putExtra("foodCal", arrayFoodList.get(position).getSumOfCal());
        startActivity(back);
    }

    public void backToMain(View view) {
        back = new Intent(this,MainPageActivity.class);
        back.putExtra("id",userId);
        startActivity(back);
    }
}