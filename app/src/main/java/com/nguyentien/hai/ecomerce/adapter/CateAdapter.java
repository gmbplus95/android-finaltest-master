package com.nguyentien.hai.ecomerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.model.Cate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CateAdapter extends BaseAdapter {

    ArrayList<Cate> cates;
    Context context;

    public CateAdapter(ArrayList<Cate> cates, Context context) {
        this.cates = cates;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cates.size();
    }

    @Override
    public Object getItem(int position) {
        return cates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView tvCate;
        ImageView imgCate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_cate, null);
            viewHolder.tvCate = (TextView) convertView.findViewById(R.id.tvCate);
            viewHolder.imgCate = (ImageView) convertView.findViewById(R.id.imvCate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cate cate = (Cate) getItem(position);
        viewHolder.tvCate.setText(cate.getCateName());
        Picasso.with(context).load(cate.getCateImage())
                .placeholder(R.drawable.nonimage)
                .error(R.drawable.error)
                .into(viewHolder.imgCate);
        return convertView;
    }
}










