package com.example.yst.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.Activity.Activity_detailActivity;
import com.example.yst.R;
import com.example.yst.adapter.ImageAdapter;
import com.example.yst.adapter.HomeActivityAdapter;
import com.example.yst.bean.Activities;
import com.example.yst.bean.ImageBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerviewActivities;
    private HomeActivityAdapter homeActivityAdapter;
    private List<Activities> activities;
    private Unbinder mUnbinder;
    private View mView;
    private Context mContext;
    private String activity_id ;
    private Integer act_views;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    // 轮播图部分
    private Banner mBanner;
    private List<ImageBean> mList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 对应页面 活动列表
        mContext = getActivity();
        if (R.layout.fragment_home != 0) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            mBanner = (Banner) mView.findViewById(R.id.banner);
            initData();
            initView();
            return mView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
    private void initView() {
        mBanner.setAdapter(new ImageAdapter(HomeFragment.this.getActivity(), mList));
        mBanner.isAutoLoop(true);
//        设置轮播指示器(显示在banner上)
        mBanner.setIndicator(new CircleIndicator(this.getContext()));
        mBanner.start();
    }

    // 轮播图的图片来源
    private void initData(){
        mList.add(new ImageBean(R.mipmap.door));
        mList.add(new ImageBean(R.mipmap.sdu));
        mList.add(new ImageBean(R.mipmap.lib));
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        recyclerviewActivities=view.findViewById(R.id.recyclerview_activity);
        initialize();
    }


    public HomeActivityAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new HomeActivityAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
//            Toast.makeText(getContext()," 点击了 "+position,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeFragment.this.getActivity(), Activity_detailActivity.class);
            if (! activities.isEmpty()) {
                Activities activity = new Activities();
                activity.setObjectId( activities.get(position).getObjectId());
                activity_id = activities.get(position).getObjectId();
                intent.putExtra("activityid",activity_id);
                act_views= activities.get(position).getViews();
                activity.setViews(act_views+1);
                activity.update(activity_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            System.out.println("更新成功！232323233231");
                        }else{
                            Log.e("更新失败", "原因: ", e);
                        }
                    }
                });

            }
            HomeFragment.this.getActivity().startActivity(intent);
            (HomeFragment.this.getActivity()).finish();
        }
    };
    private void initialize() {
        homeActivityAdapter = new HomeActivityAdapter(getActivity(),activities, onRecyclerviewItemClickListener);
        recyclerviewActivities.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        queryActivityData();
    }



    private void queryActivityData() {
        BmobQuery<Activities> activityBmobQuery = new BmobQuery<>();
        activityBmobQuery.findObjects(new FindListener<Activities>() {
            @Override
            public void done(List<Activities> object, BmobException e) {
                if (e == null) {
                    activities = object;
                    homeActivityAdapter.setActivityLists(activities);
                    recyclerviewActivities.setAdapter(homeActivityAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
}

