package com.nguyentien.hai.ecomerce.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.adapter.LaptopAdapter;
import com.nguyentien.hai.ecomerce.adapter.LaptopAdapter;
import com.nguyentien.hai.ecomerce.model.Product;
import com.nguyentien.hai.ecomerce.ultil.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarLaptop;
    ListView lvLaptop;
    LaptopAdapter LaptopAdapter;
    ArrayList<Product> products;
    int idLaptop = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        addControl();
        getIdCate();
        addEvent();
    }

    private void addEvent() {

    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.urlLaptop + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("minID", String.valueOf(idLaptop));
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolbar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIdCate() {
        idLaptop = getIntent().getIntExtra("cateIdLaptop", -1);
        Log.d("Cate Laptop value: ", idLaptop + "");
    }

    private void addControl() {
        toolbarLaptop = (Toolbar) findViewById(R.id.toolbarLaptop);
        lvLaptop = (ListView) findViewById(R.id.lvLaptop);
        products = new ArrayList<>();
        LaptopAdapter = new LaptopAdapter(getApplicationContext(), products);
        lvLaptop.setAdapter(LaptopAdapter);
    }
}
