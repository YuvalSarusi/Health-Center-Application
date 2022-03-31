package com.example.myapplication;

import android.graphics.Bitmap;

import com.google.android.material.tabs.TabItem;

public class Food {
    private String nameOfFood;
    private int sumOfCal;
    private int sumOfCar;
    private int sumOfPro;
    private int sumOfFat;
    private Bitmap photo;

    public Food(String name, int cal, int car, int pro, int fat, Bitmap photo){
        setNameOfFood(name);
        setSumOfCal(cal);
        setSumOfCar(car);
        setSumOfFat(fat);
        setSumOfPro(pro);
        setPhoto(photo);
    }

    public Food(Food copyFood){
        setNameOfFood(copyFood.getNameOfFood());
        setSumOfCal(copyFood.getSumOfCal());
        setSumOfCar(copyFood.getSumOfCar());
        setSumOfFat(copyFood.getSumOfFat());
        setSumOfPro(copyFood.getSumOfPro());
        setPhoto(copyFood.getPhoto());
    }

    public String getNameOfFood() {
        return nameOfFood;
    }
    public void setNameOfFood(String nameOfFood) {
        this.nameOfFood = nameOfFood;
    }

    public int getSumOfCal() {
        return sumOfCal;
    }
    public boolean setSumOfCal(int sumOfCal) {
        if(sumOfCal >=0 ) {
            this.sumOfCal = sumOfCal;
            return true;
        }
        else
            return false;
    }

    public int getSumOfCar() {
        return sumOfCar;
    }

    public boolean setSumOfCar(int sumOfCar) {
        if(sumOfCar >=0 && sumOfCar <=100) {
            this.sumOfCar = sumOfCar;
            return true;
        }
        else
            return false;
    }

    public int getSumOfPro() {
        return sumOfPro;
    }

    public boolean setSumOfPro(int sumOfPro) {
        if(sumOfPro >=0 && sumOfPro <=100) {
            this.sumOfPro = sumOfPro;
            return true;
        }
        else
            return false;
    }

    public int getSumOfFat() {
        return sumOfFat;
    }

    public boolean setSumOfFat(int sumOfFat) {
        if(sumOfFat >=0 && sumOfFat <=100) {
            this.sumOfFat = sumOfFat;
            return true;
        }
        else
            return false;
    }
    public Bitmap getPhoto(){
        return this.photo;
    }
    public void setPhoto(Bitmap photo){
        this.photo = photo;
    }
}
