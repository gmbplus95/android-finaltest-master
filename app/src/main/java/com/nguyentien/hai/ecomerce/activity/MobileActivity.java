package com.nguyentien.hai.ecomerce.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.adapter.MobileAdapter;
import com.nguyentien.hai.ecomerce.model.Product;
import com.nguyentien.hai.ecomerce.ultil.CheckConnection;
import com.nguyentien.hai.ecomerce.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobileActivity extends AppCompatActivity {

    Toolbar toolbarMobile;
    ListView lvMobile;
    MobileAdapter mobileAdapter;
    ArrayList<Product> mProducts;
    View footerView;

    mHandler mHandler;
    boolean isLoading = false;
    boolean limitData = false;

    int idMobile = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        addControl();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdCate();
            addEvent();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối !");
            finish();
        }

    }

    private void addEvent() {
        actionToolbar();
        getData(page);
        lvMobile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), mProducts.get(position).getId() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MobileDetailActivity.class);
                Product product = mProducts.get(position);
                intent.putExtra("mobileDetail", product);
                startActivity(intent);

            }
        });
        lvMobile.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Keo den dau bat su kien den do
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Keo bat su kien lien tuc
                // Get end position
                if (view.getLastVisiblePosition() == totalItemCount - 1 && totalItemCount != 0 && isLoading == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.urlMobile + String.valueOf(Page);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null && response.length() != 0) {
                    lvMobile.removeFooterView(footerView);
                    int mId = 0;
                    String mName = "";
                    double mPrice = 0;
                    String mImage;
                    String mDescription;
                    int mCateId;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            mId = jsonObject.getInt("id");
                            mName = jsonObject.getString("name");
                            mPrice = jsonObject.getDouble("price");
                            mImage = jsonObject.getString("image");
                            mDescription = jsonObject.getString("description");
                            mCateId = jsonObject.getInt("cateId");
                            mProducts.add(new Product(mId, mName, mPrice, mImage, mDescription, mCateId));
                            mobileAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    lvMobile.removeFooterView(footerView);
                    limitData = true;
                    Toast.makeText(getApplicationContext(), "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
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

    private void getIdCate() {
        idMobile = getIntent().getIntExtra("cateIdMobile", -1);
        Log.d("Cate mobile value: ", idMobile + "");
    }

    private void addControl() {
        toolbarMobile = (Toolbar) findViewById(R.id.toolbarMobile);
        lvMobile = (ListView) findViewById(R.id.lvMobile);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.footer_view, null);
        mProducts = new ArrayList<>();
        mobileAdapter = new MobileAdapter(getApplicationContext(), mProducts);
        lvMobile.setAdapter(mobileAdapter);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    lvMobile.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            super.run();
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
        }
    }

}
