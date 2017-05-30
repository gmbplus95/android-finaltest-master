package com.nguyentien.hai.ecomerce.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {

    Context context;
    ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_product, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvProductPrice.setText("Giá: " + decimalFormat.format(product.getPrice()) + "VND");
        Picasso.with(context).load(product.getImage())
                .placeholder(R.drawable.nonimage)
                .error(R.drawable.error)
                .into(holder.imvProduct);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imvProduct;
        public TextView tvProductName, tvProductPrice;

        public ItemHolder(View itemView) {
            super(itemView);
            imvProduct = (ImageView) itemView.findViewById(R.id.imvNewProduct);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvNewProductPrice);
            tvProductName = (TextView) itemView.findViewById(R.id.tvNewProductName);
        }
    }
}
