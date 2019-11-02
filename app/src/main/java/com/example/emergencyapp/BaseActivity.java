package com.example.emergencyapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;




import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FrameLayout viewContent;
    private TextView mTitle;

    private int menuId;
    private String menuStr;

    private OnClickListener onClickListenerTopLeft;   //左边图标的点击事件
    private OnClickListener onClickListenerTopRight;
    //定义接口
    public interface OnClickListener {
        void onClick();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_bar_base);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewContent = (FrameLayout)findViewById(R.id.view_content);
        mTitle = (TextView)findViewById(R.id.tv_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater.from(this).inflate(getContentView(),viewContent);
        ButterKnife.bind(this);
        init(savedInstanceState);

        if (hasBack()) setTopLeftButton(R.mipmap.back, new OnClickListener() {
            @Override
            public void onClick() {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onClickListenerTopLeft.onClick();
        }else if(item.getItemId() == R.id.menu_1) {
            onClickListenerTopRight.onClick();
        }
        return true;    //自己处理点击事件
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuId != 0 || !TextUtils.isEmpty(menuStr)) {
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar,menu);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuId != 0) {
            menu.findItem(R.id.menu_1).setIcon(menuId);
        }

        if (!TextUtils.isEmpty(menuStr)) {
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    protected void setTopRightButton(String menuStr,OnClickListener rightListener) {
        this.onClickListenerTopRight = rightListener;
        this.menuStr = menuStr;
    }

    protected void setTopRightButton(String menuStr,int menuId, OnClickListener rightListener) {
        this.menuStr = menuStr;
        this.menuId = menuId;
        this.onClickListenerTopRight = rightListener;

    }

    protected boolean hasBack(){
        return true;
    }

    //添加一个方法设置图标资源id和监听器
    protected void setTopLeftButton(int iconId,OnClickListener listener) {
        toolbar.setNavigationIcon(iconId);
        this.onClickListenerTopLeft = listener;    //接口回调
    }

    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    protected abstract int getContentView();

    protected abstract void init(Bundle saveInstanceState);

}
