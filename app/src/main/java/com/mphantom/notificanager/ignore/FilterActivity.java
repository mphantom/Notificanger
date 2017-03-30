package com.mphantom.notificanager.ignore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mphantom.notificanager.R;

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
