package com.nguyentien.hai.ecomerce.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nguyentien.hai.ecomerce.R;
import com.nguyentien.hai.ecomerce.adapter.CateAdapter;
import com.nguyentien.hai.ecomerce.adapter.ProductAdapter;
import com.nguyentien.hai.ecomerce.model.Cate;
import com.nguyentien.hai.ecomerce.model.Product;
import com.nguyentien.hai.ecomerce.ultil.CheckConnection;
import com.nguyentien.hai.ecomerce.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    NavigationView navigationView;
    ListView lvMainScreen;
    DrawerLayout drawerLayout;

    ArrayList<Cate> cates;
    CateAdapter cateAdapter;
    int cateId = 0;
    String cateName = "";
    String cateImage = "";

    ArrayList<Product> newProducts;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addComponent();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            addEvent();
            getCatesData();
            getNewProduct();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }

    private void getNewProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int pId = 0;
                    String pName = "";
                    double pPrice = 0;
                    String pImage;
                    String pDescription;
                    int pCateId;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            pId = jsonObject.getInt("id");
                            pName = jsonObject.getString("name");
                            pPrice = jsonObject.getDouble("price");
                            pImage = jsonObject.getString("image");
                            pDescription = jsonObject.getString("description");
                            pCateId = jsonObject.getInt("cateId");
                            newProducts.add(new Product(pId, pName, pPrice, pImage, pDescription, pCateId));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

    private void getCatesData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlCate, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            cateId = jsonObject.getInt("cateId");
                            cateName = jsonObject.getString("cateName");
                            cateImage = jsonObject.getString("cateImage");
                            cates.add(new Cate(cateId, cateName, cateImage));
                            cateAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    cates.add(new Cate(0, "Địa điểm", "http://icons.iconarchive.com/icons/alecive/flatwoken/512/Apps-Google-Maps-icon.png"));
                    cates.add(new Cate(0, "Liên hệ", "https://maxcdn.icons8.com/Color/PNG/96/Business/business_contact-96.png"));
                    cates.add(new Cate(0, "Thông tin", "https://maxcdn.icons8.com/Color/PNG/96/Very_Basic/info-96.png"));
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

    private void addEvent() {
        actionBar();
        actionViewFlipper();
        catchOnItemListView();
        recyclerViewMain.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void catchOnItemListView() {
        lvMainScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MobileActivity.class);
                            intent.putExtra("cateIdMobile", cates.get(position).getCateId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("cateIdLapTop", cates.get(position).getCateId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, CustomMapActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void actionViewFlipper() {
        ArrayList<String> arrAD = new ArrayList<>();
        arrAD.add("http://media.van.vn/Thumbnail/XXL/ContentUpload//hungvv/tu_van/diem_danh_13_dien_thoai_thong_minh_tot_nhat_tren_the_gioi_1-550.jpg");
        arrAD.add("http://media.van.vn/Thumbnail/XXL/ContentUpload//hungvv/tu_van/diem_danh_13_dien_thoai_thong_minh_tot_nhat_tren_the_gioi_2-550.jpg");
        arrAD.add("http://media.van.vn/Thumbnail/XXL/ContentUpload//hungvv/tu_van/diem_danh_13_dien_thoai_thong_minh_tot_nhat_tren_the_gioi_4-550.jpg");
        arrAD.add("http://media.van.vn/Thumbnail/XXL/ContentUpload//hungvv/tu_van/diem_danh_13_dien_thoai_thong_minh_tot_nhat_tren_the_gioi_7-550.jpg");
        arrAD.add("http://media.van.vn/Thumbnail/XXL/ContentUpload//hungvv/tu_van/diem_danh_13_dien_thoai_thong_minh_tot_nhat_tren_the_gioi_8-550.jpg");
        for (int i = 0; i < arrAD.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrAD.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animationSlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animationSlideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animationSlideIn);
        viewFlipper.setOutAnimation(animationSlideOut);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void addComponent() {

        toolbar = (Toolbar) findViewById(R.id.tbMainScreen);
        viewFlipper = (ViewFlipper) findViewById(R.id.vfMain);
        recyclerViewMain = (RecyclerView) findViewById(R.id.recyclerViewMain);
        lvMainScreen = (ListView) findViewById(R.id.lvMainScreen);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutMain);

        cates = new ArrayList<>();
        cates.add(0, new Cate(0, "Trang chủ", "https://maxcdn.icons8.com/Color/PNG/96/Very_Basic/home-96.png"));
        cateAdapter = new CateAdapter(cates, getApplicationContext());
        lvMainScreen.setAdapter(cateAdapter);

        newProducts = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), newProducts);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewMain.setAdapter(productAdapter);
    }
}
