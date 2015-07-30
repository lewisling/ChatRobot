package com.example.ahtcfg24.baymax;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivityMain extends Activity {
    private CircleImageView circleImageView;
    private double screenWidth;
    private double screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getScreenSize();

        circleImageView = (CircleImageView) findViewById(R.id.circle_imageview);

        //创建按钮
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.baymax_button));
        FloatingActionButton floatButton = new FloatingActionButton.Builder(this)
                .setContentView(icon).build();
        //创建菜单选项
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        //选项1
        ImageView item1 = new ImageView(this);
        item1.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000));
        SubActionButton button1 = itemBuilder.setContentView(item1).build();
        //选项2
        ImageView item2 = new ImageView(this);
        item2.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000));
        SubActionButton button2 = itemBuilder.setContentView(item2).build();
        //选项3
        ImageView item3 = new ImageView(this);
        item3.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000));
        SubActionButton button3 = itemBuilder.setContentView(item3).build();
        //创建菜单
        FloatingActionMenu floatMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(floatButton).build();


        setViewBounds();
    }

    /**
     * 获得屏幕长和宽的像素值
     */
    public void getScreenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    /**
     * 动态设置胸前圆形imageview的长宽和位置
     */
    public void setViewBounds() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) circleImageView
                .getLayoutParams();
        layoutParams.height = (int) (screenHeight * 0.08);
        layoutParams.width = layoutParams.height;
        layoutParams.setMargins((int) (screenWidth * 0.65), (int) (screenHeight * 0.53), 0, 0);
        circleImageView.setLayoutParams(layoutParams);
    }
}
