package com.example.orderspot_general.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.orderspot_general.R;
import com.example.orderspot_general.service.ChartService;
import com.example.orderspot_general.service.UserService;
import com.example.orderspot_general.util.HttpRequest;
import com.example.orderspot_general.util.Util;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChartActivity extends AppCompatActivity {
    Calendar calendar, toDayCalendar;
    UserService userService;
    LineChart lineChart;
    Button nextDateButton, previousDateButton, termSettingButton;
    TextView startDateTextView, lastDateTextView, chartTitle;
    String startDate, requestType, lineChartType, term;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chartActivityClassDefultSetting();
        chartActivityDetailSetting();

        requestUserTendencyAnalysisAndDrowLineChart();

        termSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTermSettingButtonText();
                setDateStringByInt(0);
                requestUserTendencyAnalysisAndDrowLineChart();
            }
        });

        nextDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateStringByInt(1);
                requestUserTendencyAnalysisAndDrowLineChart();
            }
        });

        previousDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateStringByInt(-1);
                requestUserTendencyAnalysisAndDrowLineChart();
            }
        });
    }

    public void chartActivityClassDefultSetting(){
        toDayCalendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        userService = new UserService();
        lineChart = findViewById(R.id.chart);
        termSettingButton = findViewById(R.id.termSettingButton);
        nextDateButton = findViewById(R.id.nextDateButton);
        previousDateButton = findViewById(R.id.previousDateButton);
        startDateTextView = findViewById(R.id.startDate);
        lastDateTextView = findViewById(R.id.lastDate);
        chartTitle = findViewById(R.id.chartTitle);
        term = "month";
        setDateStringByInt(0);
    }

    public void chartActivityDetailSetting(){
        Intent intent = getIntent();
        lineChartType = intent.getStringExtra("chartType");
        if(lineChartType.equals("userTendencyAnalysis")){
            userTendencyAnalysisSetting();
        }else if(lineChartType.equals("expenditureHistory")){
            expenditureHistorySetting();
        }
    }

    public void userTendencyAnalysisSetting(){
        chartTitle.setBackgroundResource(R.drawable.user_tendency_analysis_button);
        requestType = "guser_bias_analysis";
    }

    public void expenditureHistorySetting(){
        chartTitle.setBackgroundResource(R.drawable.expenditure_history_button);
        requestType = "guser_purchase_search";
    }

    public void requestUserTendencyAnalysisAndDrowLineChart(){
        String id = userService.getUserId();
        startDate = getCalendarString();
        String term = this.term.substring(0,1);
        try {
            String result = new HttpRequest().execute(requestType, id, startDate, term).get();
            ChartService chartService = new ChartService(startDate, term);
            chartService.lineChartDrawingByLineChartAndJsonStringAndLineChartType(lineChart, result, lineChartType);
            Log.e("request", result);
        }catch (Exception e){
            Log.e("error", e.toString());
        }
    }

    public String getCalendarString(){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(calendar.getTime());
    }

    public void setTermSettingButtonText(){
        switch (term){
            case "month" :
                term = "day";
                termSettingButton.setBackgroundResource(R.drawable.month_button);
                break;
            case "day" :
                term = "year";
                termSettingButton.setBackgroundResource(R.drawable.day_button);
                break;
            case "year" :
                term = "month";
                termSettingButton.setBackgroundResource(R.drawable.year_button);
                break;
        }

    }

    public void setDateStringByInt(int addDate){
        String startDateText = null;
        String lastDateText = null;
        switch (term){
            case "month" :
                calendar.add(Calendar.YEAR, addDate);
                if(!Util.isCalendarOfPast(toDayCalendar, calendar)){
                    calendar = Calendar.getInstance();
                }
                startDateText = calendar.get(Calendar.YEAR)+"";
                lastDateText = "";
                break;
            case "day" :
                calendar.add(Calendar.MONTH, addDate);
                if(!Util.isCalendarOfPast(toDayCalendar, calendar)){
                    calendar = Calendar.getInstance();
                }
                startDateText = getCalendarStringByYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                lastDateText = getCalendarStringByYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                break;
            case "year" :
                startDateText = "Start";
                lastDateText = "Last";
                break;
        }
        startDateTextView.setText(startDateText);
        lastDateTextView.setText(lastDateText);
    }

    public String getCalendarStringByYearAndMonth(int year, int month){
        String dateString = null;

        if(month > 11){
            year++;
            month = 0;
        }

        if(month < 9) {
            dateString = year + "-0" + (month + 1);
        }else{
            dateString = year + "-" + (month + 1);
        }
        return dateString;
    }

}
