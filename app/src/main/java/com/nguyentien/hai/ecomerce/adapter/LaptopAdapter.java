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

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> products;

    public LaptopAdapter(Context context, ArrayList<Product> products) {
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
        public TextView tvLaptopName, tvLaptopPrice, tvLaptopDescription;
        public ImageView imvLaptopImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LaptopAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_laptop, null);
            viewHolder.tvLaptopName = (TextView) convertView.findViewById(R.id.tvLName);
            viewHolder.tvLaptopPrice = (TextView) convertView.findViewById(R.id.tvLPrice);
            viewHolder.tvLaptopDescription = (TextView) convertView.findViewById(R.id.tvLDescription);
            viewHolder.imvLaptopImage = (ImageView) convertView.findViewById(R.id.imvLImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LaptopAdapter.ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.tvLaptopName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvLaptopPrice.setText("Giá: " + decimalFormat.format(product.getPrice()) + "VND");
        viewHolder.tvLaptopDescription.setMaxLines(2);
        viewHolder.tvLaptopDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvLaptopDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.nonimage)
                .error(R.drawable.error)
                .into(viewHolder.imvLaptopImage);
        return convertView;
    }
}
