package com.humoule.swipedragrecyclerview.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.humoule.swipedragrecyclerview.R;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AboutActivity extends AppCompatActivity {

    private final int mCurrentApiVersion = Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        if (mCurrentApiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

    }

}
