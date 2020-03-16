package com.example.orderspot_general.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderspot_general.R;
import com.example.orderspot_general.util.HttpRequest;
import com.example.orderspot_general.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {
    EditText idInputText, passwordInputText, nameInputText, emailInputText, phoneInputText1, phoneInputText2;
    Button joinButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idInputText = findViewById(R.id.idInputText);
        passwordInputText= findViewById(R.id.passwordInputText);
        nameInputText = findViewById(R.id.nameInputText);
        emailInputText = findViewById(R.id.emailInputText);
        phoneInputText1 = findViewById(R.id.phoneInputText1);
        phoneInputText2 = findViewById(R.id.phoneInputText2);
        joinButton = findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinRequest();
            }
        });
    }

    public void joinRequest(){
        try{
            String id = idInputText.getText().toString();
            String password =  passwordInputText.getText().toString();
            String name = nameInputText.getText().toString();
            String email = emailInputText.getText().toString();
            String phone1 = phoneInputText1.getText().toString();
            String phone2 = phoneInputText2.getText().toString();
            String result = new HttpRequest().execute("user_join", id, password, name, email, phone1, phone2).get();

            joinResponse(result);
        }catch (Exception e){
            Log.e("JoinActivity", e.toString());
            makeToast("회원가입에 실패하였습니다.\n다시 시도해 주세요");
        }
    }

    public void joinResponse(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String success = jsonObject.getString("success");
        if(Util.isSuccess(success)){
            makeToast("회원가입에 성공하였습니다.\n로그인 후 접속해 주세요");
            finish();
        }else {
            makeToast("회원가입에 실패하였습니다.\n입력 정보를 기입해 주세요");
        }
    }

    public void makeToast(String massage){
        Toast.makeText(getApplicationContext(), massage, Toast.LENGTH_SHORT).show();
    }
}
