package com.aw.qydda.module.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private Button btnRefresh;

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
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
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
