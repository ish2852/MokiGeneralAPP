package com.example.orderspot_general.domain;

import java.util.ArrayList;

public class UserTendencyVO {
    private String categoryName;
    private ArrayList<String> date;
    private ArrayList<Integer> count;

    public void addCount(int count){
        this.count.add(count);
    }

    public void addDate(String date){
        this.date.add(date);
    }

    public UserTendencyVO(String categoryName){
        this.categoryName = categoryName;
        this.date = new ArrayList<>();
        this.count = new ArrayList<>();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public ArrayList<Integer> getCount() {
        return count;
    }

    public void setCount(ArrayList<Integer> count) {
        this.count = count;
    }
}
