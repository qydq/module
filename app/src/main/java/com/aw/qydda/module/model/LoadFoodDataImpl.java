package com.aw.qydda.module.model;

import android.util.Log;
import android.widget.Toast;

import com.aw.qydda.module.model.entity.ResponseFdModel;
import com.aw.qydda.module.model.entity.ResponseFdTngouModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qydda on 2016/11/15.
 */

public class LoadFoodDataImpl implements ILoadFoodData {
    private static String TAG = "LoadFoodDataImpl";

    @Override
    public void getData(String httpUrl, final OnDataLoadListener onDataLoadListener) {
        //模拟网络请求,这里使用xutils3.0+
        RequestParams params = new RequestParams(httpUrl);
        params.addQueryStringParameter("wd", "xUtils");
        //这里网络请求数据可以封装采用an-base框架。
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //利用Gson解析数据(加入是我们需要的result）
                Log.d(TAG, "--result--" + result);
                Gson gson = new Gson();
                ResponseFdModel rb = new ResponseFdModel();
                rb = gson.fromJson(result, ResponseFdModel.class);
                Log.d(TAG, "--rb.getTngou--" + rb.getTngou());
                JsonArray tngouArray = rb.getTngou();
                List<ResponseFdTngouModel> foodModels = new ArrayList<ResponseFdTngouModel>();
                ResponseFdTngouModel rbTngou = new ResponseFdTngouModel();
                for (int i = 0; i < tngouArray.size(); i++) {
                    JsonObject obj = tngouArray.get(i).getAsJsonObject();
                    Log.d(TAG, "--obj--" + obj);
                    rbTngou = gson.fromJson(obj, ResponseFdTngouModel.class);
                    Log.d(TAG, "--rbTngou--img--" + rbTngou.getImg());
                    foodModels.add(rbTngou);
                }
                //转化为foodModels
//                String rt = gson.toJson(result);
//                foodModels = gson.fromJson(rt, new TypeToken<List<ResponseFdTngouModel>>() {
//                }.getType());
                onDataLoadListener.onSuccess(foodModels);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                onDataLoadListener.onFailed("数据加载失败");//mvp
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });


    }
}
