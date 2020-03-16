package com.example.orderspot_general.service;

import android.util.Log;

import com.example.orderspot_general.domain.MenuVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuRecommendationService {
    private ArrayList<MenuVO> menuList;

    public ArrayList<MenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuVO> menuList) {
        this.menuList = menuList;
    }

    public void setMenuListByResult(String result){
        menuList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);

            for(int i=0; i < jsonArray.length(); i++){
                addMenuListByJsonObject(jsonArray.getJSONObject(i));
            }
        }catch (Exception e){
            Log.e("RecommendationService", e.toString());
        }
    }

    public void addMenuListByJsonObject(JSONObject jsonObject) throws Exception{
        MenuVO menuVO = new MenuVO();
        menuVO.setName(jsonObject.getString("productName"));
        menuVO.setImageName(jsonObject.getString("productImage"));
        menuList.add(menuVO);
    }
}
