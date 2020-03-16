package com.example.orderspot_general.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderspot_general.R;
import com.example.orderspot_general.domain.UserVO;
import com.example.orderspot_general.service.FirebaseMessaginService;
import com.example.orderspot_general.service.UserService;
import com.example.orderspot_general.util.HttpRequest;
import com.example.orderspot_general.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText idInputText, passwordInputText;
    Button loginButton;
    ImageView joinButton;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idInputText = findViewById(R.id.idInputText);
        passwordInputText = findViewById(R.id.passwordInputText);
        loginButton = findViewById(R.id.loginButton);
        joinButton = findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        userService = new UserService();
        UserVO userVO = userService.getUserByContext(getApplicationContext());
        if (userVO.isUser()) {
            idInputText.setText(userVO.getId());
            passwordInputText.setText(userVO.getPassword());
            loginRequest();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });
    }

    public void loginRequest() {
        String result = null;
        String id = idInputText.getText().toString();
        String password = passwordInputText.getText().toString();
        try {
            result = new HttpRequest().execute("user_login", id, password).get();
            loginResponse(result);
        } catch (Exception e) {
            Log.e("loginActivity", e.toString());
            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginResponse(String result) throws JSONException {
        String success = null;
        JSONObject jsonObject = new JSONObject(result);
        success = jsonObject.getString("success");

        if(Util.isSuccess(success)){
            setAutoLoginUser();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAutoLoginUser(){
        String id = idInputText.getText().toString();
        String password = passwordInputText.getText().toString();
        userService.setUser(id, password);
    }

}
