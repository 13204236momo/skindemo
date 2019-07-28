package com.example.skin_library.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skin_library.R;
import com.example.skin_library.SkinManager;
import com.example.skin_library.core.CustomAppCompatViewInflater;
import com.example.skin_library.core.ViewsMatch;
import com.example.skin_library.utils.ActionBarUtils;
import com.example.skin_library.utils.NavigationUtils;
import com.example.skin_library.utils.StatusBarUtils;

public class SkinActivity extends AppCompatActivity {

    private CustomAppCompatViewInflater viewInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //拦截原生加载xml的方式，自己实现
        //原生加载xml是通过解析xml文件，根据如Textview则new一个AppCompatTextView，
        //我们要做的就是不new一个AppCompatTextView，而是new一个SkinTextview（自定义view），
        //这个在xml中写一个而是new一个SkinTextview是一致的
        //以此方法偷梁换柱来实现换肤

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        if (openChangeSkin()) {
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater(context);
            }
            viewInflater.setName(name);
            viewInflater.setAttrs(attrs);
            return viewInflater.autoMatch();
        }
        return super.onCreateView(name, context, attrs);
    }


    /**
     * @return 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     */
    protected boolean openChangeSkin() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void defaultSkin(int themeColorId) {
        this.skinDynamic(null, themeColorId);
    }


    /**
     * 动态换肤（api限制：5.0版本）
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void skinDynamic(String skinPath, int themeColorId) {
        SkinManager.getInstance().loaderSkinResources(skinPath);

        if (themeColorId != 0) {
            int themeColor = SkinManager.getInstance().getColor(themeColorId);
            StatusBarUtils.forStatusBar(this, themeColor);
            NavigationUtils.forNavigation(this, themeColor);
            ActionBarUtils.forActionBar(this, themeColor);
        }

        applyViews(getWindow().getDecorView());
    }

    /**
     * 控件回调监听，匹配上则给控件执行换肤方法
     */
    protected void applyViews(View view) {
        if (view instanceof ViewsMatch) {
            ViewsMatch viewsMatch = (ViewsMatch) view;
            viewsMatch.skinnableView();
        }

        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applyViews(parent.getChildAt(i));
            }
        }
    }
}
