package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.bean.Club;
import com.example.yst.fragment.OrganizationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ClubListActivity extends BaseActivity{
    private RecyclerView recyclerViewclub;
    private ClubAdapter clubAdapter;
    private List<Club> clubs;
    private Spinner sp, sp1, sp2;
    private String kind, level, campus, search_text,club_id1,club_state;
    private ImageView arrow, search;
    private EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        recyclerViewclub=findViewById(R.id.recyclerview_club);
        sp = findViewById(R.id.level2);
        sp1 = findViewById(R.id.campus2);
        sp2 = findViewById(R.id.kind2);
        arrow=findViewById(R.id.arrow);
        et_search=findViewById(R.id.et_search);
        search=findViewById(R.id.search);
        initialize();
    }
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(ClubListActivity.this,SuperviseActivity.class);
            if (! clubs.isEmpty()) {
                Club club = new Club();
                club.setObjectId( clubs.get(position).getObjectId());
                club_id1=clubs.get(position).getObjectId();
                club_state=clubs.get(position).getClub_state();
                intent.putExtra("clubid",club_id1);
                intent.putExtra("club_state",club_state);
                startActivity(intent);
            }
        }


    };
    private void initialize() {
        clubAdapter = new ClubAdapter(this,clubs,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        String[] ltype = new String[]{"级别","校级", "院级"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ltype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp.setAdapter(adapter);
        level = (String) sp.getSelectedItem();
        String[] ctype = new String[]{"校区","燕山校区", "圣井校区", "舜耕校区", "莱芜校区"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp1.setAdapter(adapter1);
        campus = (String) sp1.getSelectedItem();
        System.out.println("campus:"+campus);
        String[] ktype = new String[]{"类型","公益", "学术", "文体", "其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ktype);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp2.setAdapter(adapter2);
        kind = (String) sp2.getSelectedItem();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                level=spinner1.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner2 = (Spinner) adapterView;
                campus=spinner2.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner3 = (Spinner) adapterView;
                kind=spinner3.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        queryData1();
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setAdapter(adapter);
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView1, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner1 = (Spinner) adapterView1;
                        level=spinner1.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                sp1.setAdapter(adapter1);
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView2, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner2 = (Spinner) adapterView2;
                        campus=spinner2.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                sp2.setAdapter(adapter2);
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView3, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner3 = (Spinner) adapterView3;
                        kind=spinner3.getItemAtPosition(position).toString();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                queryData();
            }

        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text=et_search.getText().toString();
                if( !"".equals(search_text)){
                    queryData();
                }else {
                    Toast.makeText(ClubListActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void queryData() {
        if((campus.equals("校区")&&kind.equals("类型")&&level.equals("级别")) ||( !campus.equals("校区") && kind.equals("类型") && level.equals("级别"))||
                (campus.equals("校区")&& ! kind.equals("类型")&&level.equals("级别")) || (campus.equals("校区")&&kind.equals("类型")&& ! level.equals("级别"))){
            BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
            clubBmobQuery.addWhereEqualTo("audit_state","审核通过");
            if(campus.equals("校区")&&kind.equals("类型")&&level.equals("级别")){
                clubBmobQuery.addWhereEqualTo("club_name", search_text);
            }
            if( !campus.equals("校区") && kind.equals("类型") && level.equals("级别")){
                clubBmobQuery.addWhereEqualTo("club_campus", campus);
            }
            if(campus.equals("校区")&& ! kind.equals("类型")&&level.equals("级别")){
                clubBmobQuery.addWhereEqualTo("club_category",kind);
            }
            if(campus.equals("校区")&&kind.equals("类型")&& ! level.equals("级别")){
                clubBmobQuery.addWhereEqualTo("club_rank",level);
            }
            clubBmobQuery.findObjects(new FindListener<Club>() {
                @Override
                public void done(List<Club> object, BmobException e) {
                    if (e == null) {
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    } else {
                        Log.e("查询失败", "原因: ", e);
                    }
                }
            });
        }
        if(!campus.equals("校区")&& !kind.equals("类型")&&level.equals("级别")){
            BmobQuery<Club> clubBmobQuery1 = new BmobQuery<>();
            clubBmobQuery1.addWhereEqualTo("club_campus", campus);
            BmobQuery<Club> clubBmobQuery2 = new BmobQuery<>();
            clubBmobQuery2.addWhereEqualTo("club_category",kind);
            List<BmobQuery<Club>> queries = new ArrayList<BmobQuery<Club>>();
            queries.add(clubBmobQuery1);
            queries.add(clubBmobQuery2);
            BmobQuery<Club> query = new BmobQuery<Club>();
            query.and(queries);
            query.findObjects(new FindListener<Club>() {
                @Override
                public void done(List<Club> object, BmobException e) {
                    if(e==null){
                        Log.i("bmob","first成功");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
        if(!campus.equals("校区")&&kind.equals("类型")&& !level.equals("级别")){
            BmobQuery<Club> clubBmobQuery1 = new BmobQuery<>();
            clubBmobQuery1.addWhereEqualTo("club_campus", campus);
            BmobQuery<Club> clubBmobQuery2 = new BmobQuery<>();
            clubBmobQuery2.addWhereEqualTo("club_rank",level);
            List<BmobQuery<Club>> queries = new ArrayList<BmobQuery<Club>>();
            queries.add(clubBmobQuery1);
            queries.add(clubBmobQuery2);
            BmobQuery<Club> query = new BmobQuery<Club>();
            query.and(queries);
            query.findObjects(new FindListener<Club>() {
                @Override
                public void done(List<Club> object, BmobException e) {
                    if(e==null){
                        Log.i("bmob","sencond成功");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
        if(campus.equals("校区")&& !kind.equals("类型")&&!level.equals("级别")){
            BmobQuery<Club> clubBmobQuery1 = new BmobQuery<>();
            clubBmobQuery1.addWhereEqualTo("club_rank",level);
            BmobQuery<Club> clubBmobQuery2 = new BmobQuery<>();
            clubBmobQuery2.addWhereEqualTo("club_category",kind);
            List<BmobQuery<Club>> queries = new ArrayList<BmobQuery<Club>>();
            queries.add(clubBmobQuery1);
            queries.add(clubBmobQuery2);
            BmobQuery<Club> query = new BmobQuery<Club>();
            query.and(queries);
            query.findObjects(new FindListener<Club>() {
                @Override
                public void done(List<Club> object, BmobException e) {
                    if(e==null){
                        Log.i("bmob","third成功");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
        if(campus.equals("校区")&& kind.equals("类型")&&level.equals("级别")){
            BmobQuery<Club> clubBmobQuery1 = new BmobQuery<>();
            clubBmobQuery1.addWhereEqualTo("club_campus", campus);
            BmobQuery<Club> clubBmobQuery2 = new BmobQuery<>();
            clubBmobQuery2.addWhereEqualTo("club_category",kind);
            BmobQuery<Club> clubBmobQuery3 = new BmobQuery<>();
            clubBmobQuery3.addWhereEqualTo("club_rank",level);
            BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
            clubBmobQuery.addWhereEqualTo("audit_state","审核通过");
            List<BmobQuery<Club>> queries = new ArrayList<BmobQuery<Club>>();
            queries.add(clubBmobQuery);
            queries.add(clubBmobQuery1);
            queries.add(clubBmobQuery2);
            queries.add(clubBmobQuery3);
            BmobQuery<Club> query = new BmobQuery<Club>();
            query.and(queries);
            query.findObjects(new FindListener<Club>() {
                @Override
                public void done(List<Club> object, BmobException e) {
                    if(e==null){
                        Log.i("bmob","成功");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }

    }
    private void queryData1() {
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("audit_state","审核通过");
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubs = object;
                    clubAdapter.setClubList(clubs);
                    recyclerViewclub.setAdapter(clubAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
}
