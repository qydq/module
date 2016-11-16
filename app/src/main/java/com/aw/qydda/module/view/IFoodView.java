package com.aw.qydda.module.view;

import com.aw.qydda.module.adapter.FoodDataAdapter;

/**
 * Created by qydda on 2016/11/15.
 */

public interface IFoodView {
    //显示加载进度条
    void showLoading();

    //隐藏加载进度条
    void hideLoading();

    //给ListView设置数据源
    void initData(FoodDataAdapter adapter);

    //显示错误信息
    void showErrorMsg(String errorMsg);

}
