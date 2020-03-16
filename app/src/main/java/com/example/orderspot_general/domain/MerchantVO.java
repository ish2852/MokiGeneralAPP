package com.example.orderspot_general.domain;

import java.io.Serializable;

public class MerchantVO implements Serializable {
    private String id;
    private String name;
    private String GPS;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGPS() {
        return GPS;
    }
}
