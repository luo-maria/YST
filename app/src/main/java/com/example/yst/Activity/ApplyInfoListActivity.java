package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ApplyInfoAdapter;
import com.example.yst.bean.ApplyToClublnfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class ApplyInfoListActivity extends AppCompatActivity {
    RecyclerView recyclerViewclub;
    ApplyInfoAdapter applyInfoAdapter;
    List<ApplyToClublnfo> infos;
    String applyToClublnfo_id,club_id4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_info_list);
        initialize();
    }

    private void initialize() {
        recyclerViewclub=findViewById(R.id.applyinfos);
        applyInfoAdapter = new ApplyInfoAdapter(this,infos,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Intent intent1=getIntent();
        club_id4=intent1.getStringExtra("clubid");
        System.out.println("this is club_id4::"+club_id4);
        queryData();
    }

    private void queryData() {
        BmobQuery<ApplyToClublnfo> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("club_id", club_id4);
        System.out.println("this is BmobQuery");
        clubBmobQuery.findObjects(new FindListener<ApplyToClublnfo>() {
            @Override
            public void done(List<ApplyToClublnfo> object, BmobException e) {
                if (e == null) {
                    infos = object;
                    applyInfoAdapter.setApplyToClublnfoList(infos);
                    recyclerViewclub.setAdapter(applyInfoAdapter);
                    System.out.println("this is BmobQuery555555555555");
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }

    public ApplyInfoAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ApplyInfoAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Toast.makeText(ApplyInfoListActivity.this," 点击了 "+position,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ApplyInfoListActivity.this,ApplicationDetailsActivity.class);
            if (! infos.isEmpty()) {
                ApplyToClublnfo applyToClublnfo = new ApplyToClublnfo();
                applyToClublnfo.setObjectId( infos.get(position).getObjectId());
                applyToClublnfo_id=infos.get(position).getObjectId();
                intent.putExtra("applyToClublnfo_id",applyToClublnfo_id);
            }
            startActivity(intent);
            finish();
        }
    };
}
