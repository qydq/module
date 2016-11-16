package com.aw.qydda.module.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aw.qydda.module.R;
import com.aw.qydda.module.model.entity.ResponseFdTngouModel;
import com.aw.qydda.module.model.utils.Constant;

import org.xutils.x;

import java.util.List;

/**
 * Created by qydda on 2016/11/15.
 */

public class FoodDataAdapter extends BaseAdapter {
    private List<ResponseFdTngouModel> list;
    private Context context;
    private LayoutInflater inflater;
    private static String TAG = "FoodDataAdapter";

    public FoodDataAdapter(List<ResponseFdTngouModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ResponseFdTngouModel foodEntity = list.get(position);
        holder.keywords.setText(foodEntity.getKeywords());
        holder.description.setText(foodEntity.getDescription());

        String img = Constant.BASE_IMAGE + foodEntity.getImg();
        x.image().bind(holder.iv, img);
//        Log.d(TAG, "--img--" + img);
//        Picasso.with(context).load(foodEntity.getImg()).centerCrop().resizeDimen(R.dimen.iv_width, R.dimen.iv_width).into(holder.iv);
        return convertView;
    }

    class ViewHolder {
        TextView description, keywords;
        ImageView iv;

        public ViewHolder(View itemView) {
            description = (TextView) itemView.findViewById(R.id.description);
            keywords = (TextView) itemView.findViewById(R.id.keywords);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
