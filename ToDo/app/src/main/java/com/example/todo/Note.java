package com.example.todo;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.UUID;

public class Note {

    private String text;
    private String date;
    private String ID;

    Note(String text, String date){
        this.text = text;
        this.date = date;
        ID = UUID.randomUUID().toString();
    }

    public String getText() {
        return text;
    }

    public String getId(){
        return ID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }


    @Override
    public  String toString(){
        return text + " " + date;
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}