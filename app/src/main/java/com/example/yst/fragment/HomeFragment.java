package com.example.yst.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.Activity.Activity_detailActivity;
import com.example.yst.R;
import com.example.yst.adapter.ImageAdapter;
import com.example.yst.adapter.ActivityAdapter;
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

public class HomeFragment extends Fragment {

    RecyclerView recyclerviewActivities;
    ActivityAdapter activityAdapter;
    List<Activities> activities;
    private Unbinder mUnbinder;
    private View mView;
    private Context mContext;
    String activity_id ;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        recyclerviewActivities=view.findViewById(R.id.recyclerview_activity);

        initialize();
    }


    public ActivityAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ActivityAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            Toast.makeText(getContext()," 点击了 "+position,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeFragment.this.getActivity(), Activity_detailActivity.class);
            if (! activities.isEmpty()) {
                Activities activity = new Activities();
                activity.setObjectId( activities.get(position).getObjectId());
                activity_id = activities.get(position).getObjectId();
                intent.putExtra("activityid",activity_id);
            }
            HomeFragment.this.getActivity().startActivity(intent);
            (HomeFragment.this.getActivity()).finish();
        }
    };
    private void initialize() {
        activityAdapter = new ActivityAdapter(getActivity(),activities, onRecyclerviewItemClickListener);
        recyclerviewActivities.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        queryActivityData();
    }

    private void initView() {
        mBanner.setAdapter(new ImageAdapter(HomeFragment.this.getActivity(), mList));
        mBanner.isAutoLoop(true);
        mBanner.setIndicator(new CircleIndicator(this.getContext()));
        mBanner.start();
    }

    // 轮播图的图片来源
    private void initData(){
        mList.add(new ImageBean(R.mipmap.a));
        mList.add(new ImageBean(R.mipmap.b));
        mList.add(new ImageBean(R.mipmap.xie));
        mList.add(new ImageBean(R.mipmap.tai));
        mList.add(new ImageBean(R.mipmap.wen));
    }

    private void queryActivityData() {
        BmobQuery<Activities> activityBmobQuery = new BmobQuery<>();
        activityBmobQuery.findObjects(new FindListener<Activities>() {
            @Override
            public void done(List<Activities> object, BmobException e) {
                if (e == null) {
                    activities = object;
                    activityAdapter.setActivityLists(activities);
                    recyclerviewActivities.setAdapter(activityAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
}

