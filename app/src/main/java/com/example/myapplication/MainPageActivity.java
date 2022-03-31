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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
//
    Intent moveFromMenu;
    AlertDialog.Builder alertDialog;
    boolean userWantExit;
    String userName ="";
    long userId =0;
    int userHeight, userWeight;
    double userBmi =0;
    TextView helloUserNameText, bmiUserText, bmiUserMessage, sumOfCalText, foodInfo;
    int foodChoseCal, sumOfAllFoods =0;

    DBFoodTable getListOfFood;
    DBHelper getUserInfo;
    DBUserCalc setUserCalc;

    Food userChose;

    ArrayList<Food> arrayFoodList;
    ListView listView;
    ArrayAdapter<Food> adapter;

    int sumOfCal =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();
        getUserInfo();
        makeHelloText();
        makeBmiText();
        makeBmiMessage();
        makeAlertDialog();
    }

    public void init(){
        Intent getUserNameFromLogin = getIntent();
        userId = getUserNameFromLogin.getLongExtra("id",0);
        getUserInfo = new DBHelper(this);
        setUserCalc = new DBUserCalc(this);
        getListOfFood = new DBFoodTable(this);


        getFood();

        foodInfo = findViewById(R.id.foodInfo);

        alertDialog = new AlertDialog.Builder(this);
        arrayFoodList = new ArrayList<>();
        if (!getListOfFood.selectAll(userId).isEmpty())
            arrayFoodList = getListOfFood.selectAll(userId);
        /*else{
            ArrayList<Food> tempArrayList = new ArrayList<Food>();
            tempArrayList.add(new Food())
        }*/
        listView = findViewById(R.id.foodList);
        adapter = new ArrayAdapter<Food>(this, android.R.layout.simple_list_item_1, arrayFoodList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        sumOfCalText = findViewById(R.id.sumOfCal);
        Intent getFoodChose = getIntent();
        foodChoseCal = getFoodChose.getIntExtra("foodCal", 0);
        //sumOfAllFoods+=foodChoseCal;
        sumOfCalText.setText("Today Cal: "+ sumOfAllFoods);
    }

    public void getUserInfo(){
        userName = getUserInfo.getUserName(userId);
        userHeight = getUserInfo.getUserHeight(userId);
        userWeight = getUserInfo.getUserWeight(userId);
        double userHeightMeter = ((double)userHeight/100);
        userBmi = (userWeight)/(Math.pow(userHeightMeter,2));
    }

    private void getFood() {
        Intent getFood = getIntent();
        String name =getFood.getStringExtra("foodName");
        int cal = getFood.getIntExtra("foodCal",0);
        int car= getFood.getIntExtra("foodCar",0);
        int pro= getFood.getIntExtra("foodPro",0);
        int fat= getFood.getIntExtra("foodFat",0);
        Bitmap photo =(Bitmap) getFood.getParcelableExtra("foodPhoto");
        setUserCalc.setNewValues(userId,cal,car,fat,pro);
        userChose = new Food(name,cal,car,fat,pro,photo);
    }



    public void sumFood(View view) {
        moveFromMenu = new Intent(this, sumTodayFood.class);
        startActivity(moveFromMenu);
    }

    public void makeAlertDialog(){
        alertDialog.setTitle("Exit From App");
        alertDialog.setMessage("Are You Sure That You Want To Exit From The App?");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do actions required here ->
                // for example – here we remove element from adapter
                // adapter.remove(adapter.getItem(position));
                //  adapter.notifyDataSetChanged();
                // remove from data base
                userWantExit = false;
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do actions required here ->
                // for example – here we remove element from adapter
                // adapter.remove(adapter.getItem(position));
                //  adapter.notifyDataSetChanged();
                // remove from data base
                userWantExit = true;
                dialog.dismiss();
            }
        });
    }

    public void makeHelloText(){
        helloUserNameText = findViewById(R.id.helloUser);
        helloUserNameText.setText("Welcome, "+userName+"!");
    }

    public void makeBmiText(){
        bmiUserText = findViewById(R.id.bmiUserText);
        bmiUserText.setText("Your BMI is: "+userBmi);
    }

    public void makeBmiMessage(){
        bmiUserMessage = findViewById(R.id.bmiUserMessage);
        String bmiStatus;
        if (userBmi < 20)
            bmiStatus = "too Low";
        else if (userBmi < 25)
            bmiStatus = "excellent!";
        else if (userBmi < 30)
            bmiStatus = "little too much";
        else
            bmiStatus = "too much";
        bmiUserMessage.setText("this is "+bmiStatus);
    }

    public void makeSumOfCal(){
        sumOfCalText.setText("Sum of calories that did ate today is: "+sumOfCal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu MyMenu) {
        getMenuInflater().inflate(R.menu.my_menu, MyMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuFood){
            moveFromMenu = new Intent(this, FoodList.class);
        }
        else if(id == R.id.menuExplain){
            moveFromMenu = new Intent(this, ExplainActivity.class);
        }
        else if (id == R.id.changeInfo){
            moveFromMenu = new Intent(this, CheckRealUser.class);
        }
        else{
            AlertDialog exitDialog = alertDialog.create();
            exitDialog.show();
            if(userWantExit){
                moveFromMenu = new Intent(this, MainActivity.class);
            }
        }
        moveFromMenu.putExtra("id",userId);
        startActivity(moveFromMenu);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        foodInfo.setText(
                "Cal:"+arrayFoodList.get(position).getSumOfCal()+"," +
                        " Car:"+arrayFoodList.get(position).getSumOfCar()+"," +
                        " Pro:"+arrayFoodList.get(position).getSumOfPro()+"," +
                        " Fat:"+arrayFoodList.get(position).getSumOfFat()
        );
    }
}