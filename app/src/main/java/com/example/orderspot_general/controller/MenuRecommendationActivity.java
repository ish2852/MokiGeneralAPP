package com.example.orderspot_general.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_general.R;
import com.example.orderspot_general.service.MenuRecommendationService;
import com.example.orderspot_general.service.UserService;
import com.example.orderspot_general.service.Adepter.MenuRecommendationRecyclerViewAdepter;
import com.example.orderspot_general.util.HttpRequest;

public class MenuRecommendationActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView menuRecommendationRecyclerView;
    MenuRecommendationService recommendationService;
    Button menuRecommendationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recommendation);

        menuRecommendationRecyclerView = findViewById(R.id.menuRecommendationRecyclerView);
        menuRecommendationButton = findViewById(R.id.menuRecommendationButton);
        recommendationService = new MenuRecommendationService();

        makeMenuList();
        connectRecyclerView();

        menuRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void makeMenuList(){
        String result = getRecommendationList();
        recommendationService.setMenuListByResult(result);
    }

    public String getRecommendationList(){
        String result = null;
        try {
            String userId = new UserService().getUserId();
            result = new HttpRequest().execute("guser_recommendation_list", userId).get();
        } catch (Exception e) {
            Log.e("RecommendationActivity", e.toString());
        }
        return result;
    }

    public void connectRecyclerView(){
        menuRecommendationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuRecommendationRecyclerViewAdepter(recommendationService.getMenuList());
        menuRecommendationRecyclerView.setAdapter(adapter);
    }
}
