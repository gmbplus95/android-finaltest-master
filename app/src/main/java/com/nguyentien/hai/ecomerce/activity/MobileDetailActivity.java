package com.nguyentien.hai.ecomerce.activity;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.model.Product;
import com.nguyentien.hai.ecomerce.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

public class MobileDetailActivity extends AppCompatActivity {

    Toolbar toolbarMobile;
    TextView txttensp, txtgiasp, txtmotasp;
    Spinner spinner;
    ImageView imvMobileName;
    Button btnmuahang;
    int mId = 0;
    String mName = "";
    double mPrice = 0;
    String mImage = "";
    String mDescription = "";
    int mCateId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_detail);
        addControl();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getProduct();
            addEvent();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối !");
            finish();
        }
    }

    private void getProduct() {
        Product p = (Product) getIntent().getSerializableExtra("mobileDetail");
        mId = p.getId();
        mName = p.getName();
        mPrice = p.getPrice();
        mImage = p.getImage();
        mDescription = p.getDescription();
        mCateId = p.getCateId();
        txttensp.setText(mName);
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("###,###,###");
        txtmotasp.setText(mDescription);
        txtgiasp.setText("Gia :"+decimalFormat.format(mPrice)+" VND");
        Picasso.with(getApplicationContext()).load(mImage).placeholder(R.drawable.nonimage).error(R.drawable.error).into(imvMobileName);

    }

    private void addControl() {

        imvMobileName = (ImageView) findViewById(R.id.imgChitiet);
        toolbarMobile = (Toolbar) findViewById(R.id.toolbarchitietsp);
        txtgiasp = (TextView) findViewById(R.id.giachitiet);
        txttensp = (TextView) findViewById(R.id.tenChitiet);
        txtmotasp = (TextView) findViewById(R.id.chitietmotasp);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnmuahang = (Button) findViewById(R.id.muahang);

    }

    private void addEvent() {
        actionToolbar();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarMobile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMobile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
