package com.aw.qydda.module.model.entity;

import java.io.Serializable;

/**
 * Created by qydda on 2016/11/15.
 */

public class ResponseFdTngouModel implements Serializable {
    private String img;
    private String keywords;
    private String description;

    public ResponseFdTngouModel() {
    }

    public ResponseFdTngouModel(String img, String keywords, String description) {
        this.img = img;
        this.keywords = keywords;
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ResponseFdTngouModel{" +
                "img='" + img + '\'' +
                ", keywords='" + keywords + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
