package com.example.demo.myapplication;

/**
 * Created by Sumedha on 6/20/15.
 */
public class metricCalculator {
    static int bluetooth_rating=0;
    static int getroot_rating=0;
    static int getLocation_rating=0;
    static int getSecurity_rating=0;
    static double user_security=0;
    static int i;
    static double total_security;
    static double getWifi_rating=0;
   public static void getBluetooth(int value){
        bluetooth_rating=value;
    }
    public static void getLocation(int value){
        getLocation_rating=value;

    }
    public static void getroot(int value){
        getroot_rating=value;

    }
    public static void getsecurity(int value){
        getSecurity_rating=value;
    }
    public static void getwifi(int value){
        getWifi_rating=value;
    }
    public static void getphoneinfo(String value){

        if(value=="Good"){
            i=4;
        }
        if(value=="Very Good"){
            i=5;
        }
        else{
            i=2;
        }
    }

    public static double rating_calculator(){
        user_security=((bluetooth_rating)*0.2)+((getroot_rating)*0.3)+((getSecurity_rating)*0.3)+((getWifi_rating)*0.1)+((getLocation_rating)*0.1);
    return user_security;
    }
    public static double total_security(){

        total_security= (((user_security)*0.5)+ (i)*0.5);
        return total_security;
    }
}
