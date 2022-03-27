package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.util.ConstantConfig;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditClubActivity extends AppCompatActivity {
    EditText clubname_edit,leadername_edit,leadercall_edit,clubintro_edit;
    Spinner sp,sp1,sp2;
    Button create_edit;
    String club_id6,level_edit,campus_edit,kind_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club);
        initView();

    }

    private void setMyClub() {
        Intent intent1=getIntent();
        club_id6=intent1.getStringExtra("clubid");
        System.out.println("this is club_id6::"+club_id6);
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id6, new QueryListener<Club>() {
            @Override
            public void done(Club club, BmobException e) {
                if(e==null ){
                    if (club.getClub_name()!= null) {
                       clubname_edit.setText(club.getClub_name());
                    }
                    if (club.getClub_intro()!= null) {
                        clubintro_edit.setText(club.getClub_intro());
                    }
                    if (club.getClub_president()!= null) {
                        leadername_edit.setText(club.getClub_president());
                    }
                    if (club.getPre_number()!= null) {
                        leadercall_edit.setText(club.getPre_number());
                    }

                }else{
                    Toast.makeText(EditClubActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        clubname_edit=findViewById(R.id.clubname_edit);
        leadername_edit=findViewById(R.id.leadername_edit);
        leadercall_edit=findViewById(R.id.leadercall_edit);
        clubintro_edit=findViewById(R.id.clubintro_edit);
        create_edit=findViewById(R.id.create_edit);
        setMyClub();
        String[] ltype = new String[]{"校级", "院级"};
        sp=(Spinner) super.findViewById(R.id.level_edit);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ltype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                level_edit=spinner1.getItemAtPosition(position).toString();
//                System.out.println("这里的级别是:"+level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp1 = (Spinner) super.findViewById(R.id.campus_edit);
        String[] ctype = new String[]{"燕山校区", "圣井校区","舜耕校区","莱芜校区"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                campus_edit=spinner1.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp2 = (Spinner) super.findViewById(R.id.kind_edit);
        String[] ktype = new String[]{"公益", "学术","文体","其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ktype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                kind_edit=spinner1.getItemAtPosition(position).toString();
//                System.out.println("这里的级别是:"+level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
//        clublogo_edit.setOnClickListener(new View.OnClickListener() {
//            private Object SelectPhotoActivity;
//
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(ConstantConfig.SELECT_PHOTO, ConstantConfig.UPDATE_HEAD_IMAGES);
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                intent.setClass(EditClubActivity.this , SelectPhotoActivity.class);
//                startActivity(intent);
//
//            }
//        });
        create_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club thisclub=new Club();
                thisclub.setClub_name(clubname_edit.getText().toString());
                thisclub.setClub_intro(clubintro_edit.getText().toString());
                thisclub.setPre_number(leadercall_edit.getText().toString());
                thisclub.setClub_president(leadername_edit.getText().toString());
                thisclub.setClub_category(kind_edit);
                thisclub.setClub_rank(level_edit);
                thisclub.setClub_campus(campus_edit);
                thisclub.update(club_id6, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(EditClubActivity.this,"修改成功:"+clubname_edit.getText().toString(),Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EditClubActivity.this, HomeActivity.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(EditClubActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
