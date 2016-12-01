package com.aw.qydda.module.model.entity;

import java.io.Serializable;

/**
 * Created by qydda on 2016/11/30.
 */

public class ResponseTtModel implements Serializable {
    private String name;
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
