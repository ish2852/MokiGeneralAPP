package com.example.orderspot_general.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orderspot_general.R;
import com.example.orderspot_general.service.MenuRecommendationService;
import com.example.orderspot_general.service.UserService;
import com.example.orderspot_general.util.HttpRequest;

public class HomeActivity extends AppCompatActivity {
    Button userTendencyAnalysisButton, expenditureHistoryButton, menuRecommendationButton, logoutButton;
    TextView userIdTextView;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userService = new UserService();
        userIdTextView = findViewById(R.id.userIdTextView);
        logoutButton = findViewById(R.id.logoutButton);
        userTendencyAnalysisButton = findViewById(R.id.userTendencyAnalysisButton);
        expenditureHistoryButton =findViewById(R.id.expenditureHistoryButton);
        menuRecommendationButton = findViewById(R.id.menuRecommendationButton);

        userIdTextView.setText(userService.getUserId());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpRequest().execute("guser_token_update", userService.getUserId(), null);
                userService.userLogoutByContext(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userTendencyAnalysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChartActivity("userTendencyAnalysis");
            }
        });


        expenditureHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChartActivity("expenditureHistory");
            }
        });

        menuRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuRecommendationActivity.class);
                startActivity(intent);
            }
        });

    }

    public void goChartActivity(String chartName){
        Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
        intent.putExtra("chartType", chartName);
        startActivity(intent);
    }
}
