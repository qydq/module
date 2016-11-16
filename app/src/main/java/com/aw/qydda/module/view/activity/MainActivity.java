package com.aw.qydda.module.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aw.qydda.module.R;
import com.aw.qydda.module.adapter.FoodDataAdapter;
import com.aw.qydda.module.presenter.FoodPresenter;
import com.aw.qydda.module.view.IFoodView;

public class MainActivity extends AppCompatActivity implements IFoodView {
    private ListView listView;
    private FoodPresenter presenter = new FoodPresenter(this);
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _initView();
        presenter.loadData();
    }

    private void _initView() {
        listView = (ListView) findViewById(R.id.lv);
        progressBar = (ProgressBar) findViewById(R.id.pb);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void initData(FoodDataAdapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
