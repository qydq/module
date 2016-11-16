package com.aw.qydda.module.model;

import com.aw.qydda.module.model.entity.ResponseFdTngouModel;

import java.util.List;

/**
 * Created by qydda on 2016/11/15.
 */

public interface OnDataLoadListener {
    void onSuccess(List<ResponseFdTngouModel> foodModes);

    void onFailed(String errorMsg);
}
