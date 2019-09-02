package com.example.skindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.skin_library.base.SkinActivity;

public class StartActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    //内置换肤
    public void skinLocal(View view) {
        startActivity(new Intent(this,SkinLocalActivity.class));
    }

    public void skinOut(View view) {
        startActivity(new Intent(this,SkinOutActivity.class));
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
