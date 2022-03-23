package com.example.yst.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.bean.Club;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class OrganizationFragment extends Fragment {
    RecyclerView recyclerViewclub;
    ClubAdapter clubAdapter;
    List<Club> clubs;
    private Unbinder mUnbinder;
    private View mView;
    private Context mContext;
    Spinner sp, sp1, sp2;
    String kind, level, campus, search_text;
    Intent intent;
    ImageView arrow, search;
    EditText et_search;


    public static OrganizationFragment newInstance() {
        OrganizationFragment fragment = new OrganizationFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (R.layout.fragment_organization != 0) {
            mView = inflater.inflate(R.layout.fragment_organization, container, false);
            return mView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        recyclerViewclub=view.findViewById(R.id.recyclerview_club);
        sp = view.findViewById(R.id.level2);
        sp1 = view.findViewById(R.id.campus2);
        sp2 = view.findViewById(R.id.kind2);
        arrow=view.findViewById(R.id.arrow);
        et_search=view.findViewById(R.id.et_search);
        initialize();


    }
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position

//            Toast.makeText(getContext()," 点击了 "+position,Toast.LENGTH_SHORT).show();
        }
    };
    private void initialize() {
//        clubAdapter = new ClubAdapter(getContext());

        clubAdapter = new ClubAdapter(getActivity(),clubs,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        String[] ltype = new String[]{"校级", "院级"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ltype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp.setAdapter(adapter);
        level = (String) sp.getSelectedItem();
        String[] ctype = new String[]{"燕山校区", "圣井校区", "舜耕校区", "莱芜校区"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp1.setAdapter(adapter1);
        campus = (String) sp1.getSelectedItem();
        System.out.println("campus:"+campus);
        String[] ktype = new String[]{"公益", "学术", "文体", "其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ktype);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp2.setAdapter(adapter2);
        kind = (String) sp2.getSelectedItem();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
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

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });
        queryData();
//        recyclerViewclub.mOnRecyclerviewItemClickListener

    }


    private void queryData() {
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubs = object;
                    clubAdapter.setClubList(clubs);
//                    System.out.println("this is clubs:"+clubs);
                    recyclerViewclub.setAdapter(clubAdapter);

                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }

    String club_id1;
    private void showDetail(int position) {
        if (! clubs.isEmpty()) {
            Club club = new Club();
            club.setObjectId( clubs.get(position).getObjectId());
            club_id1=clubs.get(position).getObjectId();
            System.out.println("this is clubid"+club_id1);
        }
    }
}


