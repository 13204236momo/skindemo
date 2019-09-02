package com.example.skindemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.skin_library.base.SkinActivity;
import com.example.skin_library.utils.PreferencesUtils;

import java.io.File;

public class ThreeActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        String  skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "net163.skin";
        if (("net163").equals(PreferencesUtils.getString(this, "currentSkin"))) {
            skinDynamic(skinPath, R.color.skin_item_color);
        } else {
            defaultSkin(R.color.colorPrimary);
        }
    }

    @Override
    protected boolean openChangeSkin() {
        return true;
    }
}
