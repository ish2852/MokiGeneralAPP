package com.example.orderspot_general.domain;

import java.util.ArrayList;

public class UserExpenditureHistoryVO {
    private ArrayList<String> date;
    private ArrayList<Integer> sales;

    public UserExpenditureHistoryVO(){
        date = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public void addSales(int count){
        this.sales.add(count);
    }

    public void addDate(String date){
        this.date.add(date);
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    public ArrayList<Integer> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Integer> sales) {
        this.sales = sales;
    }
}
