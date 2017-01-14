package com.mphantom.notificanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class IgnoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignore);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, IgnoreActivity.class));
    }
}
