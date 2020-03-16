package com.example.orderspot_general.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.orderspot_general.domain.UserVO;

public class UserService {
    SharedPreferences sharedPreference;
    static UserVO userVO = new UserVO();

    public void setUser(String id, String password){
        setUserVO(id, password);

        SharedPreferences.Editor autoLogin = sharedPreference.edit();
        autoLogin.putString("id", id);
        autoLogin.putString("password", password);

        if(firebaseTokenCheck() == null){
            String token = new FirebaseMessaginService().sendRegistrationToServerByToken();
            autoLogin.putString("token", token);
        }

        autoLogin.commit();
    }

    private String firebaseTokenCheck(){
        return sharedPreference.getString("token", null);
    }

    public void setUserVO(String id, String password) {
        userVO.setId(id);
        userVO.setPassword(password);
    }

    public UserVO getUserByContext(Context context){
        sharedPreference  = context.getSharedPreferences("user", Context.MODE_PRIVATE);

        userVO.setId(getAutoLoginUserId());
        userVO.setPassword(getAutoLoginUserPassword());

        return userVO;
    }

    public String getAutoLoginUserId(){
        return sharedPreference.getString("id", null);
    }

    public String getAutoLoginUserPassword(){
        return sharedPreference.getString("password", null);
    }

    public String getUserId(){
        return userVO.getId();
    }

    public void userLogoutByContext(Context context){
        setUserVO(null, null);
        sharedPreference  = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor autoLogin = sharedPreference.edit();
        autoLogin.putString("id", null);
        autoLogin.putString("password", null);
        autoLogin.putString("token", null);
        autoLogin.commit();
    }
}
