package com.example.yst.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class SuperviseActivity extends BaseActivity {
    private TextView clubname_edit1,leadername_edit1,leadercall_edit1,clubintro_edit1,clubcampus1,clubkind1;
    private Button create_edit1,cancellation;
    private String club_id6,level_edit,campus_edit,kind_edit,imagePath;
    private ImageView logo_edit1;
    private RadioGroup rg3;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise);
        initView();
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });
        cancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SuperviseActivity.this).setTitle("社团注销")
                        .setMessage("您确定要注销该社团吗？此操作不可撤回，请与社团领导人协商好。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Club club=new Club();
                                club.delete(club_id6, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null) {
                                            Toast.makeText(SuperviseActivity.this,"注销成功:",Toast.LENGTH_SHORT).show();
                                            Intent intent1 = new Intent(SuperviseActivity.this, ManageClubActivity.class);
                                            intent1.putExtra("clubid",club_id6);
                                            startActivity(intent1);
                                        }else{
                                            Log.e("注销失败","原因：",e);
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                            }

                        })
                        .show();
            }
        });
    }
    private void setMyClub() {
        Intent intent1=getIntent();
        club_id6=intent1.getStringExtra("clubid");
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id6, new QueryListener<Club>() {
            @Override
            public void done(Club club, BmobException e) {
                if(e==null ){
                    clubname_edit1.setText(club.getClub_name());
                    clubintro_edit1.setText(club.getClub_intro());
                    leadername_edit1.setText(club.getClub_president());
                    leadercall_edit1.setText(club.getPre_number());
                    if(!club.getLogo_url().equals("")){
                        logo_edit1.setImageBitmap(BitmapFactory.decodeFile(club.getLogo_url()));
                    }else{
                        logo_edit1.setImageResource(R.mipmap.san);
                    }
                    clubcampus1.setText(club.getClub_campus());
                    clubkind1.setText(club.getClub_category());
                }else{
                    Toast.makeText(SuperviseActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initView() {
        clubname_edit1=findViewById(R.id.clubname_edit1);
        leadername_edit1=findViewById(R.id.leadername_edit1);
        leadercall_edit1=findViewById(R.id.leadercall_edit1);
        clubintro_edit1=findViewById(R.id.clubintro_edit1);
        create_edit1=findViewById(R.id.create_edit1);
        logo_edit1=findViewById(R.id.clublogo_edit1);
        cancellation=findViewById(R.id.cancellation);
        rg3=findViewById(R.id.rg3);
        clubcampus1=findViewById(R.id.clubcampus1);
        clubkind1=findViewById(R.id.clubkind1);
        setMyClub();

        create_edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club thisclub=new Club();
                thisclub.setClub_rank(level_edit);
                thisclub.update(club_id6, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(SuperviseActivity.this,"修改成功:"+clubname_edit1.getText().toString(),Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(SuperviseActivity.this, ManageClubActivity.class);
                            intent1.putExtra("clubid","92ec346797");
                            startActivity(intent1);
                        } else {
                            Toast.makeText(SuperviseActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void selectRadioBtn(){
        radioButton = findViewById(rg3.getCheckedRadioButtonId());
        level_edit = radioButton.getText().toString();
    }
}
