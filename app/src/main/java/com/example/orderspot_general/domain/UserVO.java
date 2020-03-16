package com.example.orderspot_general.domain;

public class UserVO {
    static private String id;
    static private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUser(){
        boolean userCheck = true;

        if(id == null)
            userCheck = false;
        else if(password == null)
            userCheck = false;

        return userCheck;
    }
}
