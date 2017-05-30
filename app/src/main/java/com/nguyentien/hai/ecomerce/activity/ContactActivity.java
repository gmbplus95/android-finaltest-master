package com.nguyentien.hai.ecomerce.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nguyentien.hai.ecomerce.R;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbarContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbarContact = (Toolbar) findViewById(R.id.toolbarContact);
        TextView gmail = (TextView) findViewById(R.id.gmail);
        final TextView phone = (TextView) findViewById(R.id.phone);
        actionToolbar();

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailInt = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "nuceshop@gmail.com", null));
                mailInt.putExtra(Intent.EXTRA_SUBJECT, "");
                mailInt.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(mailInt, "Send mail"));
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneInt = new Intent(Intent.ACTION_DIAL);
                phoneInt.setData(Uri.parse("tel:0936119295"));
                startActivity(phoneInt);
            }
        });
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarContact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
