package com.example.orderspot_general.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderspot_general.R;
import com.example.orderspot_general.domain.MenuVO;
import com.example.orderspot_general.domain.MerchantVO;
import com.example.orderspot_general.service.OrderService;
import com.example.orderspot_general.service.Adepter.OrderListRecyclerViewAdepter;
import com.example.orderspot_general.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView orderListRecyclerView;
    OrderService orderService;
    EditText orderRequirement;
    Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        defultSetting();
        connectRecyclerView();

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = orderService.orderRequestByContextAndRequirement(orderRequirement.getText().toString());
                waitActivityMoveByResult(result);

            }
        });
    }

    public void defultSetting(){
        orderListRecyclerView = findViewById(R.id.orderListRecyclerView);
        orderRequirement = findViewById(R.id.orderRequirement);
        orderButton = findViewById(R.id.orderButton);

        Intent intent = getIntent();
        orderService = new OrderService();
        orderService.setMerchantVO((MerchantVO) intent.getSerializableExtra("merchant"));
        orderService.setOrderList((ArrayList<MenuVO>) intent.getSerializableExtra("orderList"));
        orderService.setContext(getApplicationContext());
    }

    public void connectRecyclerView(){
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderListRecyclerViewAdepter(orderService.getOrderList());
        orderListRecyclerView.setAdapter(adapter);
    }

    public void waitActivityMoveByResult(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            String seccess = jsonObject.getString("success");
            if(Util.isSuccess(seccess)){
                Intent intent = new Intent(OrderActivity.this, OrderWaitActivity.class);
                intent.putExtra("orderId", jsonObject.getInt("order"));
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "주문에 실패했습니다. 잠시후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Log.e("OrderAvtivity", e.toString());
        }
    }
}
