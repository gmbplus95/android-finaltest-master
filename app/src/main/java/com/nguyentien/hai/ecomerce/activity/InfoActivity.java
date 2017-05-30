package com.nguyentien.hai.ecomerce.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nguyentien.hai.ecomerce.R;

public class InfoActivity extends AppCompatActivity {

    Toolbar toolbarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbarInfo = (Toolbar) findViewById(R.id.toolBarInfo);
        actionToolbar();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
