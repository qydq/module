package com.aw.qydda.module.model.entity;

import com.google.gson.JsonArray;

import java.io.Serializable;

/**
 * Created by qydda on 2016/11/16.
 */

public class ResponseFdModel implements Serializable {
    private boolean status;
    private String total;
    private JsonArray tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public JsonArray getTngou() {
        return tngou;
    }

    public void setTngou(JsonArray tngou) {
        this.tngou = tngou;
    }

    @Override
    public String toString() {
        return "ResponseFdModel{" +
                "status=" + status +
                ", total='" + total + '\'' +
                ", tngou=" + tngou +
                '}';
    }
}
