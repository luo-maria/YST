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
import com.example.yst.adapter.ApplyClubAdapter;
import com.example.yst.adapter.ApplyInfoAdapter;
import com.example.yst.bean.ApplyToClublnfo;
import com.example.yst.bean.Club;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ApplyClubListActivity extends BaseActivity {
    private RecyclerView recyclerViewclub;
    private ApplyClubAdapter applyClubAdapter;
    private List<Club> clubList;
    private String union_id,clubid2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_apply_list);
        initView();
    }

    private void initView() {
        recyclerViewclub=findViewById(R.id.applyclubinfos);
        applyClubAdapter = new ApplyClubAdapter(this,clubList,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Intent intent1=getIntent();
        union_id=intent1.getStringExtra("clubid");
        queryData();
    }

    private void queryData() {
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("audit_state", "未审核");
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubList = object;
                    applyClubAdapter.setApplyClubList(clubList);
                    recyclerViewclub.setAdapter(applyClubAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
    public ApplyClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ApplyClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Toast.makeText(ApplyClubListActivity.this," 点击了 "+position,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ApplyClubListActivity.this,AuditClubActivity.class);
            if (! clubList.isEmpty()) {
            ApplyToClublnfo applyToClublnfo = new ApplyToClublnfo();
            applyToClublnfo.setObjectId( clubList.get(position).getObjectId());
            clubid2=clubList.get(position).getObjectId();
            intent.putExtra("clubid2",clubid2);
            intent.putExtra("clubid",union_id);
        }
        startActivity(intent);
        finish();
    }
    };
}
