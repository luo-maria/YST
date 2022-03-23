package com.example.yst.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.yst.R;
import com.example.yst.adapter.ImageAdapter;
import com.example.yst.bean.ImageBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    private Banner mBanner;
    private List<ImageBean> mList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        mBanner = (Banner) view.findViewById(R.id.banner);
        ListView activitys = view.findViewById(R.id.activitys);



        // 为列表项设置监听器
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        SimpleAdapter simpleAdapter = new SimpleAdapter(HomeFragment.this.getContext(), data, R.layout.activity_lists, new String[] { "activity_images","club_name", "activity_name" ,"activity_intro"},
                new int[] { R.id.image,R.id.activity_create_name, R.id.activity_name,R.id.activity_intro  });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap( (Bitmap)data );
                    return true;
                }else{
                    return false;
                }
            }
        });
        activitys.setAdapter(simpleAdapter);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mBanner.setAdapter(new ImageAdapter(HomeFragment.this.getActivity(), mList));
        // Set Banner is auto to loop.
        mBanner.isAutoLoop(true);
        // Set an indicator for Banner.
        mBanner.setIndicator(new CircleIndicator(this.getContext()));
        mBanner.start();
    }
    private void initData(){
        mList.add(new ImageBean(R.mipmap.a));
        mList.add(new ImageBean(R.mipmap.b));
        mList.add(new ImageBean(R.mipmap.xie));
        mList.add(new ImageBean(R.mipmap.tai));
        mList.add(new ImageBean(R.mipmap.wen));
    }

}

