package com.example.yst.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.example.yst.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationFragment extends Fragment {
    Spinner sp, sp1, sp2;
    String kind, level, campus,search_text;
    ArrayList arr1 = new ArrayList();
    Intent intent;
    byte[] logo;
    Bitmap imagebm;
    ImageView arrow,search;
    String data1,data2,data3;
    EditText et_search;
    public static OrganizationFragment newInstance() {
        OrganizationFragment fragment = new OrganizationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_organization, container, false);
        sp = view.findViewById(R.id.level2);
        sp1 = view.findViewById(R.id.campus2);
        sp2 = view.findViewById(R.id.kind2);
        arrow = view.findViewById(R.id.arrow);
        et_search = view.findViewById(R.id.et_search);

        ListView clubs = view.findViewById(R.id.clubs);
        Intent intent1 = getActivity().getIntent();
        String username = intent1.getStringExtra("username");
        System.out.println("这里是OrganizationFragment的username:" + username);
//        DatabaseHelper dbtest = new DatabaseHelper(OrganizationFragment.this.getContext());
//        final SQLiteDatabase db = dbtest.getWritableDatabase();

        // 为列表项设置监听器
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        SimpleAdapter simpleAdapter = new SimpleAdapter(OrganizationFragment.this.getContext(), data, R.layout.club_lists, new String[]{"logo", "club_name", "level", "campus", "kind", "club_intro"},
                new int[]{R.id.logo, R.id.clubname, R.id.clublevel, R.id.clubcampus, R.id.clubkind, R.id.clubintos});
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                } else {
                    return false;
                }
            }
        });
        clubs.setAdapter(simpleAdapter);
//        clubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                intent = new Intent(OrganizationFragment.this.getActivity(), Club_detailActivity.class);
//                intent.putExtra("id", data.get(position).get("club_id").toString());
//                intent.putExtra("username",username);
//                startActivity(intent);
//            }
//        });
        search = view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text = et_search.getText().toString();
                if (search_text != null) {
//                    Intent intent = new Intent(OrganizationFragment.this.getContext(), Search_club.class);
                    Intent intent1 = getActivity().getIntent();
                    String username = intent1.getStringExtra("username");
                    intent.putExtra("username", username);
                    intent.putExtra("search_text", search_text);
                    startActivity(intent);
                }
            }
        });
        //        private void initView () {
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
        System.out.println("campus:" + campus);
        String[] ktype = new String[]{"公益", "学术", "文体", "其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(OrganizationFragment.this.getContext(), android.R.layout.simple_spinner_item, ktype);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp2.setAdapter(adapter2);
        kind = (String) sp2.getSelectedItem();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {


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
                data3 = spinner3.getItemAtPosition(position).toString();
                System.out.println("这里的data3是:" + data3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sp.setAdapter(adapter);
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView1, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner1 = (Spinner) adapterView1;
                        data1=spinner1.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView2, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner2 = (Spinner) adapterView2;
                        data2=spinner2.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data22是:"+data2);

//                sp2.setAdapter(adapter2);
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView3, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner3 = (Spinner) adapterView3;
                        data3=spinner3.getItemAtPosition(position).toString();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data33是:"+data3);
//                Intent intent = new Intent(OrganizationFragment.this.getActivity(), Select_club.class);
                Intent intent1=getActivity().getIntent();
                String username=intent1.getStringExtra("username");
                intent.putExtra("username",username);
                intent.putExtra("level",data1);
                intent.putExtra("campus",data2);
                intent.putExtra("kind",data3);
                startActivity(intent);
            }
        });
        return view;


    }}
