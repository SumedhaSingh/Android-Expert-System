package com.example.demo.myapplication;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;

public class csvreader {
    InputStream inputStream;
    public csvreader(InputStream inputStream){
        this.inputStream=inputStream;
    }
    static String returned;
    public  String rankgetter(String[] details){
        String csvFile="absolutefile.csv";




            BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));
        try{
String line;
            while((line = reader.readLine()) != null) {
                String[] fileString = line.split(",");
                if(details[0]==fileString[0]){
                    if(details[1]==fileString[1])

                        returned=details[3];


                }
                line=reader.readLine();
            }

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
    }
        finally {
            try {

                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returned;
    }

}
