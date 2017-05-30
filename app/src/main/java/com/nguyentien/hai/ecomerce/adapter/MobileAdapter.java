package com.nguyentien.hai.ecomerce.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MobileAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> products;

    public MobileAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView tvMobileName, tvMobilePrice, tvMobileDescription;
        public ImageView imvMobileImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_mobile, null);
            viewHolder.tvMobileName = (TextView) convertView.findViewById(R.id.tvMName);
            viewHolder.tvMobilePrice = (TextView) convertView.findViewById(R.id.tvMPrice);
            viewHolder.tvMobileDescription = (TextView) convertView.findViewById(R.id.tvMDescription);
            viewHolder.imvMobileImage = (ImageView) convertView.findViewById(R.id.imvMImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.tvMobileName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvMobilePrice.setText("Giá: " + decimalFormat.format(product.getPrice()) + "VND");
        viewHolder.tvMobileDescription.setMaxLines(2);
        viewHolder.tvMobileDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMobileDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.nonimage)
                .error(R.drawable.error)
                .into(viewHolder.imvMobileImage);
        return convertView;
    }
}
