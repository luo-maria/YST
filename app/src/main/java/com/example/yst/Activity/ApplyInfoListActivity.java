package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.bean.ApplyToClublnfo;

import java.util.List;


public class ApplyInfoListActivity extends AppCompatActivity {
    RecyclerView recyclerViewclub;
    ClubAdapter clubAdapter;
    List<ApplyToClublnfo> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_info_list);
        initialize();
    }

    private void initialize() {
        recyclerViewclub=findViewById(R.id.applyinfos);
//        clubAdapter = new ClubAdapter(this,infos,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

}
