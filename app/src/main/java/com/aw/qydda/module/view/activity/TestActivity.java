package com.aw.qydda.module.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aw.qydda.module.R;
import com.aw.qydda.module.model.entity.ResponseTtModel;
import com.aw.qydda.module.view.SlidingDeleteListView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
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
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private String TAG = "TestActivity";
    private List<ResponseTtModel> rbs;
    private Gson gson;
    private int recentlyChoosePosition;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


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
        recentlyChoosePosition = sp.getInt("recentlyChoosePosition", 0);
        if (!TextUtils.isEmpty(txt)) {
            Type type = new TypeToken<List<ResponseTtModel>>() {
            }.getType();
            rbs = gson.fromJson(txt, type);
            for (ResponseTtModel rb : rbs) {
                arrays.add(rb.getName());
                arrayPass.add(rb.getPass());
            }
            if (rbs.size() != 0) {
                editText1.setText(rbs.get(recentlyChoosePosition).getName());
                editText2.setText(rbs.get(recentlyChoosePosition).getPass());
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
                recentlyChoosePosition = sp.getInt("recentlyChoosePosition", 0);//这里必须要重新得到，上面initView的时候得到的值在后面回改变，所以以上方法可以丢弃了。
                //如果删除的位置大于选中的位置，则不做处理，还是之前的位置，如果等于则默认选择第一个。
                if (position > recentlyChoosePosition) {
                } else if (position == recentlyChoosePosition) {
                    edit.putInt("recentlyChoosePosition", 0);
                    edit.commit();
                } else {
                    edit.putInt("recentlyChoosePosition", recentlyChoosePosition - 1);
                    edit.commit();
                }
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
                //如果保存的数据是否存在,如果存在则先把数据清除，然后再添加数据
                for (int i = 0; i < rbs.size(); i++) {
                    if (rbs.get(i).getName().equals(name)) {
                        oldPass = rbs.get(i).getPass();
                        oldName = rbs.get(i).getName();
                        Log.d(TAG, "--qydq@@--onCreate--原账号信息--" + oldName + "--" + oldPass);
                        Log.d(TAG, "--qydq@@--onCreate--账户存在移除中...--" + i);
                        removeItemByName(arrays, oldName);//这里用两种方法，移除数据，代表方法都有用。
                        arrayPass.remove(i);//单纯的移除
                    }
                }
                rbs.clear();//保存数据用rbs减少对象的使用，并且先清空。（这里可以用DB的，但是强迫症，密码应该加密保存）
                arrays.add(name);
                arrayPass.add(pass);
                Log.d(TAG, "--qydq@@--onCreate--大小--" + arrays.size() + "-->" + arrayPass.size());
                for (int i = 0; i < arrays.size(); i++) {
                    ResponseTtModel rb = new ResponseTtModel();
                    rb.setName(arrays.get(i));
                    rb.setPass(arrayPass.get(i));
                    Log.d(TAG, "--qydq@@--onCreate--添加--" + arrays.get(i));
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
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(TestActivity.this, parent.getCount() + "click item " + position + "name:" + arrays.get(position) + "pass:" + arrayPass.get(position), Toast.LENGTH_SHORT).show();
                String recentlyChooseName = arrays.get(position);
                editText1.setText(recentlyChooseName);
                editText2.setText(arrayPass.get(position));

                edit.putInt("recentlyChoosePosition", position);
                edit.commit();

                mSlidingDeleteLv.setItemChecked(position, true);//设置某行点击了。
                mSlidingDeleteLv.setSelection(position);//设置某行选中了；

                mAdapter.setSelection(position);
//                mAdapter.notifyDataSetInvalidated();
                mAdapter.notifyDataSetChanged();//通知改变adapter

            }
        });
        mSlidingDeleteLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestActivity.this, "long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                if (obj.equals(list.get(i))) {
                    list.remove(obj);
                }
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Test Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class MyAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrays;
        private int choosePostion = -1;

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
            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, null);
                holder.mContentTv = (TextView) convertView.findViewById(R.id.content_tv);
                holder.mDeleteBtn = (Button) convertView.findViewById(R.id.delete_btn);
                holder.iv_choose_wifi = (ImageView) convertView.findViewById(R.id.iv_choose_wifi);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mContentTv.setText(mArrays.get(position));
            //必须先执行这段，再执行下一段代码。
            if (position == choosePostion) {
                holder.iv_choose_wifi.setVisibility(View.VISIBLE);
            } else {
                holder.iv_choose_wifi.setVisibility(View.INVISIBLE);
            }
            recentlyChoosePosition = sp.getInt("recentlyChoosePosition", 0);//这里必须要重新得到，上面initView的时候得到的值在后面回改变，所以以上方法可以丢弃了。

            if (position == recentlyChoosePosition) {
                holder.iv_choose_wifi.setVisibility(View.VISIBLE);
            } else {
                holder.iv_choose_wifi.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        public void setSelection(int position) {
            this.choosePostion = position;
        }
    }

    private static class ViewHolder {
        TextView mContentTv;
        Button mDeleteBtn;
        ImageView iv_choose_wifi;
    }
}
