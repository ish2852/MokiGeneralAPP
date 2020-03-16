package com.example.orderspot_general.controller;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_general.R;
import com.example.orderspot_general.domain.MenuVO;
import com.example.orderspot_general.service.MenuService;
import com.example.orderspot_general.util.HttpRequest;
import com.example.orderspot_general.service.Adepter.MenuListRecyclerViewAdepter;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    RecyclerView menuListRecyclerView;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    MenuService menuService;
    Button menuChoiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuListRecyclerView = findViewById(R.id.menuListRecyclerView);
        menuService = new MenuService();

        nfcAdapter = nfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        onNewIntent(getIntent());

        menuChoiceButton = findViewById(R.id.menuChoiceButton);
        menuChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<MenuVO> orderList = new ArrayList<>();
                for(MenuVO menuVO : menuService.getMenuList()){
                    if(menuVO.getAmount() == 1){
                        orderList.add(menuVO);
                    }
                }
                Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
                intent.putExtra("orderList", orderList);
                intent.putExtra("merchant", menuService.getMerchantVO());
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onPause() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            getMerchantShopMenuListByTag(tag);
        }
        super.onNewIntent(intent);
    }

    public void getMerchantShopMenuListByTag(Tag tag){
        String result = menuListRequestByTag(tag);
        menuService.setMenuListAndMerchant(result);
        connectRecyclerView();
    }

    public String menuListRequestByTag(Tag tag) {
        String result = null;
        try {
            String tagData = getTagDataByTag(tag);
            result = new HttpRequest().execute("products_information", tagData).get();
        } catch (Exception e) {
            Log.e("MenuActivity", e.toString());
        }
        return result;
    }

    public String getTagDataByTag(Tag tag) throws Exception {
        byte dataByte[] = null;
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        NdefMessage message = ndef.getNdefMessage();
        NdefRecord record[] = message.getRecords();
        dataByte = record[0].getPayload();
        return new String(dataByte);
    }

    public void connectRecyclerView(){
        menuListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuListRecyclerViewAdepter(menuService.getMenuList());
        menuListRecyclerView.setAdapter(adapter);
    }
}
