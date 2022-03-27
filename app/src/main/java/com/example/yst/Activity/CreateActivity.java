package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Activity;
import com.example.yst.bean.Club;
import com.example.yst.bean.Club_Activity;
import com.example.yst.util.ConstantConfig;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class CreateActivity extends AppCompatActivity {

    TextView clubName;
    ImageView activityImage;
    EditText activityName,leaderName,leaderCall,activityIntro;
    Button createActivity;
    String club_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity);
        initView();
    }

    private void initView() {
        Intent intent1=getIntent();
        club_id=intent1.getStringExtra("clubid");
        System.out.println("this is club_id4::"+club_id);
        // 表单信息
        clubName = findViewById(R.id.clubName);
        activityName = findViewById(R.id.activityName);
        leaderName = findViewById(R.id.leaderName);
        leaderCall = findViewById(R.id.leaderCall);
        activityIntro = findViewById(R.id.activity_intro);
        // 创建按钮
        createActivity = findViewById(R.id.createActivity);
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id, new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
                if(e==null){
                    clubName.setText(object.getClub_name());
                }else{
                    Toast.makeText(CreateActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 活动宣传图
//        activityImage.setOnClickListener(new View.OnClickListener() {
//            private Object SelectPhotoActivity;
//
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(ConstantConfig.SELECT_PHOTO, ConstantConfig.UPDATE_HEAD_IMAGES);
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                intent.setClass(CreateActivity.this , SelectPhotoActivity.class);
//                startActivity(intent);
//            }
//        });


        // 查询社团名称并显示到页面 TextView上  id：clubName

//        // 根据社团ID查询社团名称————社团ID由上一个页面的当前所在社团 传值过来
//        String clubName = "";
//        String club_id = "";
//        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
//        bmobQuery.getObject("clubID", new QueryListener<Club>() {
//            @Override
//            public void done(Club object,BmobException e) {
//                if(e==null){
//                    System.out.println(object.getClub_name().toString());
//                }else{
//                    System.out.println("查询失败：" + e.getMessage());
//                }
//            }
//        });

        // 创建活动 按钮的实现
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = new Activity();
//                activity.setActivity_club_name(clubName.getText().toString());
                activity.setActivity_name(activityName.getText().toString());
                activity.setActivity_leader(leaderName.getText().toString());
                activity.setActivity_leader_phone(leaderCall.getText().toString());
                activity.setActivity_info(activityIntro.getText().toString());
                activity.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(CreateActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //关联社团活动表
                Club_Activity clubActivity=new Club_Activity();
                System.out.println("这里的创建活动对应的club_id:"+ club_id);
                clubActivity.setClub_id(club_id);

                // 获取新创建的活动的ID
                String Activity_id = "";
                clubActivity.setActivity_id(Activity_id);
                clubActivity.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(CreateActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Toast.makeText(CreateActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                //刷新本页面
                Intent intent=new Intent(CreateActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
