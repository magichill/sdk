package com.bingdou.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * 支付方式对象类
 * Created by gaoshan on 16/12/17.
 */
public class PayTypeModel {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("desc")
    private String description;
    @SerializedName("limit")
    private int limit;
    @SerializedName("display")
    private int display;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

}
