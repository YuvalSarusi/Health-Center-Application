package com.example.myapplication;

import android.graphics.Bitmap;

public class Meal extends Food{
    private Food [] mealsFoods = new Food[2];

    public Meal(String name, int extraCal, int extraCar, int extraPro, int extraFat, Food [] foods, Bitmap photo) {
        super(name, foods[0].getSumOfCal()+foods[1].getSumOfCal() + extraCal, foods[0].getSumOfCar()+foods[1].getSumOfCar() +extraCar, foods[0].getSumOfPro()+foods[1].getSumOfPro()+extraPro,foods[0].getSumOfFat()+foods[1].getSumOfFat()+extraFat, photo);
        for (int i =0; i<mealsFoods.length; i++){
            mealsFoods[i] = new Food(foods[i].getNameOfFood(), foods[i].getSumOfCal(), foods[i].getSumOfCar(), foods[i].getSumOfPro(),foods[i].getSumOfFat(), foods[i].getPhoto());
        }
    }
}
