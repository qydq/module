package com.aw.qydda.module.model;

/**
 * Created by qydda on 2016/11/15.
 */

public interface ILoadFoodData {
    void getData(String httpUrl, OnDataLoadListener onDataLoadListener);
}