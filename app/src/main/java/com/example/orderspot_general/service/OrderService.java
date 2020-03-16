package com.example.orderspot_general.service;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.orderspot_general.domain.MenuVO;
import com.example.orderspot_general.domain.MerchantVO;
import com.example.orderspot_general.domain.UserVO;
import com.example.orderspot_general.util.HttpRequest;

import org.json.JSONObject;

import java.util.List;

public class OrderService {
    private List<MenuVO> orderList;
    private MerchantVO merchantVO;
    private Context context;

    public String orderRequestByContextAndRequirement(String requirement) {
        String result = null;
        try {
            String orderInformation = makeOrderInformationByRequirement(requirement);
            result = new HttpRequest().execute("user_order", orderInformation).get();
        } catch (Exception e) {
            Log.e("OrderActivity", e.toString());
        }
        return result;
    }

    public String makeOrderInformationByRequirement(String requirement) throws Exception{
        UserVO user = new UserService().getUserByContext(context);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("muser_ID", merchantVO.getId());
        jsonObject.put("guser_ID", user.getId());
        jsonObject.put("requirement", requirement);
        jsonObject.put("amount", makeAmountList());
        return jsonObject.toString();
    }


    public JSONObject makeAmountList() throws Exception{
        JSONObject jsonObject = new JSONObject();
        for(MenuVO menuVO : orderList){
            jsonObject.put(menuVO.getId(), menuVO.getAmount());
        }
        return jsonObject;
    }

    public List<MenuVO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<MenuVO> orderList) {
        this.orderList = orderList;
    }

    public MerchantVO getMerchantVO() {
        return merchantVO;
    }

    public void setMerchantVO(MerchantVO merchantVO) {
        this.merchantVO = merchantVO;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
