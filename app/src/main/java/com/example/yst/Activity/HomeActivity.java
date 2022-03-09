package com.example.yst.Activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.yst.R;
import com.example.yst.adapter.MyPagerAdapter;
import com.example.yst.bean.ImageBean;
import com.example.yst.entity.TabEntity;
import com.example.yst.fragment.HomeFragment;
import com.example.yst.fragment.HomePageFragment;
import com.example.yst.fragment.OrganizationFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    initView();
//    initData();
//    initLayout();}
    private Banner mBanner;

    private List<ImageBean> mList = new ArrayList<>();
    private String[] mTitles = {"社团活动", "校园社团", "个人中心"};
    //未选中（一 一对应）
    private int[] mIconUnselectIds = {
            R.mipmap.home_unselect, R.mipmap.collect_unselect,
            R.mipmap.my_unselect};
    //选中显示
    private int[] mIconSelectIds = {
            R.mipmap.home_selected0, R.mipmap.collect_selected0,
            R.mipmap.my_selected0};

    //存放Fragment的集合
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
        Intent intent = getIntent();
        String homeactivity_number = intent.getStringExtra("input_number");
        System.out.println("这里是HomeActivity的number：" + homeactivity_number);

    }

    @Override
    protected void initData() {
        mList.add(new ImageBean(R.mipmap.a));
        mList.add(new ImageBean(R.mipmap.b));
        mList.add(new ImageBean(R.mipmap.a));
        mList.add(new ImageBean(R.mipmap.b));
        mList.add(new ImageBean(R.mipmap.a));
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(OrganizationFragment.newInstance());
        mFragments.add(HomePageFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        //切换
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }
}