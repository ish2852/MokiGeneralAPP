package com.example.orderspot_general.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.orderspot_general.R;
import com.example.orderspot_general.controller.OrderWaitActivity;
import com.example.orderspot_general.util.HttpRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;


public class FirebaseMessaginService extends FirebaseMessagingService {
    private static final String TAG = "FCM";

    public FirebaseMessaginService() {

    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.e("onNewToken", token);
        super.onNewToken(token);
        sendRegistrationToServerByToken();
    }

    public String sendRegistrationToServerByToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        try {
            String userId = UserService.userVO.getId();
            new HttpRequest().execute("guser_token_update", userId, token).get();
        } catch (Exception e) {
            Log.e("token Update", e.toString());
        }
        return token;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Log.d(TAG, "From" + from);

        if (remoteMessage.getData().size() > 0) {
            String data = remoteMessage.getData().get("data");
            sendNotification(data);
        }
        super.onMessageReceived(remoteMessage);
    }

    private void sendNotification(String data) {
        Log.e("fcm data ", data);
        String title = "Order Spot";
        String body = null;
        String type = null;
        int orderId = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            orderId = Integer.parseInt(jsonObject.getString("orderId"));
            type = jsonObject.getString("type");

            if(type.equals("merchant_completed")){
                body = jsonObject.getString("muserName")+ " : " + orderId + " 상품이 나왔습니다.";
            }else if(type.equals("merchant_order_cancel")){
                body = jsonObject.getString("muserName") + " : " + orderId + " 상품이 취소 되었습니다..";
            }

        } catch (Exception e) {
            Log.e("FCM", e.toString());
        }

        Intent intent = new Intent(this, OrderWaitActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("type", type);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP
        );


        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {300,1000,300,1000,300,1000};
        vibrator.vibrate(pattern, -1);
        getApplicationContext().startActivity(intent);
    }

}
