package com.example.yst.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.Activity.Club_detailActivity;
import com.example.yst.Activity.LoginActivity;
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
    private RecyclerView recyclerViewclub;
    private ClubAdapter clubAdapter;
    private List<Club> clubs;
    private Unbinder mUnbinder;
    private View mView;
    private Context mContext;
    private Spinner sp, sp1, sp2;
    private String kind, level, campus, search_text,club_id1,club_state;
    private ImageView arrow, search;
    private EditText et_search;


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
        search=view.findViewById(R.id.search);
        initialize();


    }
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(OrganizationFragment.this.getActivity(), Club_detailActivity.class);
            if (! clubs.isEmpty()) {
                Club club = new Club();
                club.setObjectId( clubs.get(position).getObjectId());
                club_id1=clubs.get(position).getObjectId();
                club_state=clubs.get(position).getClub_state();
                intent.putExtra("clubid",club_id1);
                intent.putExtra("club_state",club_state);
            }
            OrganizationFragment.this.getActivity().startActivity(intent);
            (OrganizationFragment.this.getActivity()).finish();
        }

    };
    private void initialize() {
        clubAdapter = new ClubAdapter(getActivity(),clubs,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        String[] ltype = new String[]{"??????","??????", "??????"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ltype);  //???????????????????????????
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //??????????????????????????????????????????
        sp.setAdapter(adapter);
        level = (String) sp.getSelectedItem();
        String[] ctype = new String[]{"??????","????????????", "????????????", "????????????", "????????????"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ctype);  //???????????????????????????
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //??????????????????????????????????????????
        sp1.setAdapter(adapter1);
        campus = (String) sp1.getSelectedItem();
        System.out.println("campus:"+campus);
        String[] ktype = new String[]{"??????","??????", "??????", "??????", "??????"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ktype);  //???????????????????????????
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //??????????????????????????????????????????
        sp2.setAdapter(adapter2);
        kind = (String) sp2.getSelectedItem();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //???????????????
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
                //???????????????
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
                //???????????????
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
                        //???????????????
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
                        //???????????????
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
                        //???????????????
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
                    Toast.makeText(OrganizationFragment.this.getActivity(),"???????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void queryData() {
        if((campus.equals("??????")&&kind.equals("??????")&&level.equals("??????")) ||( !campus.equals("??????") && kind.equals("??????") && level.equals("??????"))||
                (campus.equals("??????")&& ! kind.equals("??????")&&level.equals("??????")) || (campus.equals("??????")&&kind.equals("??????")&& ! level.equals("??????"))){
            BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
            clubBmobQuery.addWhereEqualTo("audit_state","????????????");
            if(campus.equals("??????")&&kind.equals("??????")&&level.equals("??????")){
                clubBmobQuery.addWhereEqualTo("club_name", search_text);
            }
            if( !campus.equals("??????") && kind.equals("??????") && level.equals("??????")){
                clubBmobQuery.addWhereEqualTo("club_campus", campus);
            }
            if(campus.equals("??????")&& ! kind.equals("??????")&&level.equals("??????")){
                clubBmobQuery.addWhereEqualTo("club_category",kind);
            }
            if(campus.equals("??????")&&kind.equals("??????")&& ! level.equals("??????")){
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
                        Log.e("????????????", "??????: ", e);
                    }
                }
            });
        }
        if(!campus.equals("??????")&& !kind.equals("??????")&&level.equals("??????")){
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
                        Log.i("bmob","first??????");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","?????????"+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
        if(!campus.equals("??????")&&kind.equals("??????")&& !level.equals("??????")){
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
                        Log.i("bmob","sencond??????");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","?????????"+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
        if(campus.equals("??????")&& !kind.equals("??????")&&!level.equals("??????")){
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
                        Log.i("bmob","third??????");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","?????????"+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
        if(!campus.equals("??????")&& !kind.equals("??????")&&!level.equals("??????")){
            BmobQuery<Club> clubBmobQuery1 = new BmobQuery<>();
            clubBmobQuery1.addWhereEqualTo("club_campus", campus);
            BmobQuery<Club> clubBmobQuery2 = new BmobQuery<>();
            clubBmobQuery2.addWhereEqualTo("club_category",kind);
            BmobQuery<Club> clubBmobQuery3 = new BmobQuery<>();
            clubBmobQuery3.addWhereEqualTo("club_rank",level);
            BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
            clubBmobQuery.addWhereEqualTo("audit_state","????????????");
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
                        Log.i("bmob","??????");
                        clubs = object;
                        clubAdapter.setClubList(clubs);
                        recyclerViewclub.setAdapter(clubAdapter);
                    }else{
                        Log.i("bmob","?????????"+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }

    }
    private void queryData1() {
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("audit_state","????????????");
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubs = object;
                    clubAdapter.setClubList(clubs);
                    recyclerViewclub.setAdapter(clubAdapter);
                } else {
                    Log.e("????????????", "??????: ", e);
                }
            }
        });
    }

}


