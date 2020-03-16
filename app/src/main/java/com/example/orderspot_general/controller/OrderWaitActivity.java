package com.example.orderspot_general.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderspot_general.R;
import com.example.orderspot_general.service.UserService;
import com.example.orderspot_general.util.HttpRequest;
import com.example.orderspot_general.util.Util;

import org.json.JSONObject;

public class OrderWaitActivity extends AppCompatActivity {
    TextView orderWaitTextView;
    Button completeMessageButton;
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_wait);

        orderWaitTextView = findViewById(R.id.orderWaitTextView);
        completeMessageButton = findViewById(R.id.completeMessageButton);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        settingByIntent(intent);
        super.onNewIntent(intent);
    }

    public void settingByIntent(Intent intent) {
        orderId = intent.getIntExtra("orderId", 0);
        String type = intent.getStringExtra("type");
        String orderNumberString = "주문번호 " + orderId;

        if (type == null) {
            orderNumberString += "\n주문이 정상적으로 접수되었습니다.";
            completeMessageButton.setVisibility(View.GONE);
        } else if (type.equals("merchant_completed")) {
            orderNumberString += "\n제품을 수령해 주세요.";
            completeMessageButton.setVisibility(View.VISIBLE);
            completeMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    completeMessageRequestByOrderId(orderId);
                    finish();
                }
            });
        } else if (type.equals("merchant_order_cancel")) {
            orderNumberString += "\n주문이 정상적으로 취소되었습니다..";
            completeMessageButton.setVisibility(View.VISIBLE);
            completeMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        orderWaitTextView.setText(orderNumberString);
    }

    public void completeMessageRequestByOrderId(int orderId) {
        try {
            String userId = new UserService().getUserId();
            String result = new HttpRequest().execute("general_complete_message_check", userId, Integer.toString(orderId)).get();
            String success = new JSONObject(result).getString("success");
            makeToastBySuccess(success);
        } catch (Exception e) {
            Log.e("Complete Message", e.toString());
        }
    }

    public void makeToastBySuccess(String success) {
        String toastString = null;
        if (Util.isSuccess(success)) {
            toastString = "이용해 주셔서 감사합니다.";
        } else {
            toastString = "잠시 후 다시 시도해 주세요.";
        }
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_SHORT).show();
    }
}
