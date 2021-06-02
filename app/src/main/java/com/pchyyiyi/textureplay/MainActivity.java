package com.pchyyiyi.textureplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.pchyyiyi.textureplay.model.UserAdvertInfo;
import com.pchyyiyi.textureplay.util.AndroidUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void TextureNewActivityView1(View view){
        UserAdvertInfo userAdvertInfo5 = new UserAdvertInfo();
        userAdvertInfo5.setAclass(2);
        userAdvertInfo5.setAdContent("qqqqwwwwwwwwwwww");
        userAdvertInfo5.setPicUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4067468447,1451150120&fm=11&gp=0.jpg");
        userAdvertInfo5.setVideoUrl("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4");

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(TextureNewActivity.BUNDLE_KEY_ADVERTINFO, userAdvertInfo5);
        bundle1.putFloat(TextureNewActivity.BUNDLE_KEY_AMOUNT, 44444.7f);
        AndroidUtil.startActivity(this, TextureNewActivity.class, bundle1, false);
    }

    public void TextureNewActivityView2(View view){
        UserAdvertInfo userAdvertInfo5 = new UserAdvertInfo();
        userAdvertInfo5.setAclass(2);
        userAdvertInfo5.setAdContent("a啊啊啊啊啊啊啊啊啊啊滴滴答答滴滴答答的发反反复复方法");
        userAdvertInfo5.setPicUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4067468447,1451150120&fm=11&gp=0.jpg");
        userAdvertInfo5.setVideoUrl("http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(TextureNewActivity.BUNDLE_KEY_ADVERTINFO, userAdvertInfo5);
        bundle1.putFloat(TextureNewActivity.BUNDLE_KEY_AMOUNT, 5.7f);
        AndroidUtil.startActivity(this, TextureNewActivity.class, bundle1, false);

    }
    public void TextureNewActivityView3(View view){
        UserAdvertInfo userAdvertInfo5 = new UserAdvertInfo();
        userAdvertInfo5.setAclass(2);
        userAdvertInfo5.setAdContent("a啊啊啊啊啊啊啊啊啊啊滴滴答答滴滴答答的发反反复复方法");
        userAdvertInfo5.setPicUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4067468447,1451150120&fm=11&gp=0.jpg");
        userAdvertInfo5.setVideoUrl("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4");

        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(TextureNewActivity.BUNDLE_KEY_ADVERTINFO, userAdvertInfo5);
        bundle1.putFloat(TextureNewActivity.BUNDLE_KEY_AMOUNT, 125.7f);
        AndroidUtil.startActivity(this, TextureNewActivity.class, bundle1, false);

    }
}