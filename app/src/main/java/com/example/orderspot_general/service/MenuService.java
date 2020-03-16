package com.example.orderspot_general.service;

import android.util.Log;

import com.example.orderspot_general.domain.MenuVO;
import com.example.orderspot_general.domain.MerchantVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuService {
    ArrayList<MenuVO> menuList;
    MerchantVO merchantVO;

    public void setMenuListAndMerchant(String jsonString){
        menuList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            setMerchant(jsonArray.get(0).toString());
            setMenuList(jsonArray.get(1).toString());
        }catch (Exception e){
            Log.e("MenuService", e.toString());
        }
    }

    public void setMerchant(String jsonString) throws  Exception{
        JSONObject merchant = new JSONObject(jsonString);
        merchantVO = new MerchantVO();
        merchantVO.setId(merchant.getString("muser_ID"));
        merchantVO.setName(merchant.getString("muserName"));
        merchantVO.setGPS(merchant.getString("muserGPS"));
    }

    public void setMenuList(String jsonStirng) throws  Exception{
        JSONArray menuList = new JSONArray(jsonStirng);

        JSONObject menu = null;
        for(int i=0; i < menuList.length(); i++){
            menu = menuList.getJSONObject(i);
            addMenuListByJsonObject(menu);
        }
    }

    public void addMenuListByJsonObject(JSONObject jsonObject) throws Exception{
        MenuVO menuVO = new MenuVO();
        menuVO.setId(jsonObject.getString("product_ID"));
        menuVO.setName(jsonObject.getString("productName"));
        menuVO.setPrice(jsonObject.getInt("productPrice"));
        menuVO.setImageName(jsonObject.getString("productImage"));
        menuList.add(menuVO);
    }

    public ArrayList<MenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuVO> menuList) {
        this.menuList = menuList;
    }

    public MerchantVO getMerchantVO() {
        return merchantVO;
    }

    public void setMerchantVO(MerchantVO merchantVO) {
        this.merchantVO = merchantVO;
    }
}
