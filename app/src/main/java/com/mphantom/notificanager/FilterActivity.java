package com.mphantom.notificanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FilterActivity extends AppCompatActivity {


    public static void start(Context context) {
        context.startActivity(new Intent(context, FilterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }
}
