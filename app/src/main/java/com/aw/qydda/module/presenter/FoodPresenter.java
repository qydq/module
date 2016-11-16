package com.aw.qydda.module.presenter;

import android.content.Context;
import android.os.Handler;

import com.aw.qydda.module.adapter.FoodDataAdapter;
import com.aw.qydda.module.model.ILoadFoodData;
import com.aw.qydda.module.model.LoadFoodDataImpl;
import com.aw.qydda.module.model.OnDataLoadListener;
import com.aw.qydda.module.model.entity.ResponseFdTngouModel;
import com.aw.qydda.module.model.utils.Constant;
import com.aw.qydda.module.view.IFoodView;

import java.util.List;

/**
 * Created by qydda on 2016/11/15.
 * 关于性能问题分享知识点：
 * Tips：根据个人经验，绝大部分泄漏是由于使用单例模式hold住了Activity的引用，
 * 比如传入了context或者将Activity作为listener设置了进去，
 * 所以在使用单例模式的时候要特别注意，还有在Activity生命周期结束的时候将一些自定义监听器的Activity引用置空。
 * 关于LeakCanary的更多分析可以看项目主页的介绍，
 * 还有这里http://www.liaohuqiu.net/cn/posts/leak-canary-read-me/
 */

public class FoodPresenter {
    private IFoodView iFoodView;
    private ILoadFoodData iLoadFoodData;
    private Context context;
    private Handler handler = new Handler();

    public FoodPresenter(IFoodView iFoodView) {
        this.iFoodView = iFoodView;
        this.context = (Context) iFoodView;//这里不用传递Context过来，性能问题。
        iLoadFoodData = new LoadFoodDataImpl();
    }

    public void loadData() {
        iFoodView.showLoading();
        iLoadFoodData.getData(Constant.LIST_URL, new OnDataLoadListener() {
            @Override
            public void onSuccess(final List<ResponseFdTngouModel> foodModes) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFoodView.initData(new FoodDataAdapter(foodModes, context));
                        iFoodView.hideLoading();
                    }
                });
            }

            @Override
            public void onFailed(final String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iFoodView.showErrorMsg(errorMsg);
                        iFoodView.hideLoading();
                    }
                });

            }
        });
    }
}
