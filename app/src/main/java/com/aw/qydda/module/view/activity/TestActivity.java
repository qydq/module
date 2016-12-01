package com.aw.qydda.module.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aw.qydda.module.R;
import com.aw.qydda.module.model.entity.ResponseTtModel;
import com.aw.qydda.module.view.SlidingDeleteListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qydda on 2016/11/28.
 */
public class TestActivity extends Activity {
    private SlidingDeleteListView mSlidingDeleteLv;
    private MyAdapter mAdapter;
    private ArrayList<String> arrays;
    private ArrayList<String> arrayPass;

    private Button btn;
    private EditText editText1;
    private EditText editText2;
    private boolean isExist = false;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private String TAG = "TestActivity";
    private List<ResponseTtModel> rbs;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

//        arrays = new ArrayList<String>(Arrays.asList(Util.arrays));

        mSlidingDeleteLv = (SlidingDeleteListView) findViewById(R.id.sliding_delete_lv);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.btn);
        sp = getSharedPreferences("TestActivity", Activity.MODE_PRIVATE);
        edit = sp.edit();
        arrays = new ArrayList<>();
        arrayPass = new ArrayList<>();

        rbs = new ArrayList<ResponseTtModel>();
        gson = new Gson();
        //----------------------//
        String txt = sp.getString("gson", null);
        if (!TextUtils.isEmpty(txt)) {
            Type type = new TypeToken<List<ResponseTtModel>>() {
            }.getType();
            rbs = gson.fromJson(txt, type);
            for (ResponseTtModel rb : rbs) {
                arrays.add(rb.getName());
                arrayPass.add(rb.getPass());
            }
        }
        mAdapter = new MyAdapter(this, arrays);
//        mSlidingDeleteLv.setButtonID(R.id.delete_btn);
        mSlidingDeleteLv.setAdapter(mAdapter);
        mSlidingDeleteLv.setOnItemButtonShowingListener(new SlidingDeleteListView.OnItemButtonShowingListener() {

            @Override
            public void onShowButton(View button) {
            }

            @Override
            public void onHideButton(View button) {
            }

            @Override
            public void onButtonClick(View button, int position) {
                removeItemByPosition(position);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString().trim();
                String pass = editText2.getText().toString().trim();
                String oldPass = null;
                String oldName = null;
                for (ResponseTtModel rb : rbs) {
                    if (rb.getName().equals(name)) {
                        isExist = true;
                        oldPass = rb.getPass();
                        oldName = rb.getName();
                    }
                }
                //如果保存的数据是否存在,如果存在则先把数据清除，然后再添加数据
                if (isExist) {
                    removeItemByName(arrays, oldName);
                    removeItemByName(arrayPass, oldPass);
                } else {
                }
                rbs.clear();//保存数据用rbs减少对象的使用，并且先清空。（这里可以用DB的，但是强迫症，密码应该加密保存）
                isExist = false;
                arrays.add(name);
                arrayPass.add(pass);
                for (int i = 0; i < arrays.size(); i++) {
                    ResponseTtModel rb = new ResponseTtModel();
                    rb.setName(arrays.get(i));
                    rb.setPass(arrayPass.get(i));
                    rbs.add(rb);
                }
                String json = gson.toJson(rbs);
                Log.d(TAG, "--qydq@@--json--" + json);
                edit.putString("gson", json);
                edit.commit();
                //排序
//                arrays.clear();
//                arrayPass.clear();
//                for (int i = rbs.size() - 1; i > 0; i--) {
//                    arrays.add(rbs.get(i).getName());
//                    arrayPass.add(rbs.get(i).getPass());
//                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mSlidingDeleteLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View parent, int position,
                                    long id) {
                Toast.makeText(TestActivity.this, "click item " + position + "name:" + arrays.get(position) + "pass:" + arrayPass.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        mSlidingDeleteLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(TestActivity.this, "long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void removeItemByPosition(int position) {
        arrays.remove(position);
        arrayPass.remove(position);
        mAdapter.notifyDataSetChanged();
        rbs.clear();
        for (int i = 0; i < arrays.size(); i++) {
            Log.d(TAG, "--qydq@@--removeItem--rbs--" + arrays.get(i));
            ResponseTtModel rb = new ResponseTtModel();
            rb.setName(arrays.get(i));
            rb.setPass(arrayPass.get(i));
            rbs.add(rb);
        }
        String json = gson.toJson(rbs);
        Log.d(TAG, "--qydq@@--removeItem--json--" + json);
        edit.putString("gson", json);
        edit.commit();
    }

    public void removeItemByName(List<String> list, String obj) {
        if (list.size() == 0) {
        } else {
            for (int i = 0; i < arrays.size(); i++) {
                if (obj == list.get(i)) {
                    list.remove(obj);
                }
            }
        }
    }

    public class MyAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrays;

        public MyAdapter(Context c, ArrayList<String> data) {
            mContext = c;
            mArrays = data;
        }

        @Override
        public int getCount() {
            return mArrays.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrays.get(position);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.layout_list_item, null);
                holder.mContentTv = (TextView) convertView.findViewById(R.id.content_tv);
                holder.mDeleteBtn = (Button) convertView.findViewById(R.id.delete_btn);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mContentTv.setText(mArrays.get(position));

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView mContentTv;
        Button mDeleteBtn;
    }

}
