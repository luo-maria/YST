package com.example.yst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Activities;
import com.example.yst.bean.Club;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView clubName;
    private ImageView activityImage;
    private EditText activityName,leaderName,leaderCall,activityDetails,activityPlace;
    private Button createActivity,activity_end_time,activity_start_time,selectTime;
    private String club_id,start_time,end_time,imagePath,clubname,clubLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity);
        initView();
    }

    private void initView() {
        Intent intent1=getIntent();
        club_id=intent1.getStringExtra("clubid");
        // 表单信息
        clubName = findViewById(R.id.clubName);
        activityName = findViewById(R.id.activityName);
        leaderName = findViewById(R.id.leaderName);
        leaderCall = findViewById(R.id.leaderCall);
        activityDetails = findViewById(R.id.activity_details);
        activityPlace = findViewById(R.id.activityPlace);
        activityImage=findViewById(R.id.activityImage);
        // 显示社团名称
        clubName.setText(clubname);

        // 创建活动按钮
        createActivity = findViewById(R.id.createActivity);

        // 选择日期按钮
        selectTime = findViewById(R.id.selectTime);
        // 显示时间
        activity_start_time = findViewById(R.id.activity_start_time);
        activity_end_time = findViewById(R.id.activity_end_time);


        // 查询当前社团
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id, new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
                if(e==null){
                    clubLogo = object.getLogo_url();
                    clubname=object.getClub_name();
                }else{
                    Toast.makeText(CreateActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        activityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CreateActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开系统相册
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);

                }
            }
        });
        selectTime.setOnClickListener(this::onClick);

        // 创建活动 按钮的实现
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities activity = new Activities();
                activity.setActivity_name(activityName.getText().toString());
                activity.setActivity_leader(leaderName.getText().toString());
                activity.setPhone(leaderCall.getText().toString());
                activity.setActivity_info(activityDetails.getText().toString());
                activity.setPlace(activityPlace.getText().toString());
                activity.setStart_time(start_time);
                activity.setEnd_time(end_time);
                activity.setClub_id(club_id);
                activity.setClub_logo(clubLogo);
                activity.setActivity_imgurl(imagePath);
                activity.setClub_name(clubname);
                activity.setApplynum(0);
                activity.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(CreateActivity.this,"创建活动成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateActivity.this,"创建活动失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(CreateActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                //刷新本页面
                Intent intent=new Intent(CreateActivity.this, ManageClubActivity.class);
                intent.putExtra("clubid",club_id);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectTime:
                // 弹出日历选择时间段：开始时间和结束时间
                showDialogTwo();
                break;
        }
    }

    private void showDialogTwo() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date, null);
        final DatePicker startTime = (DatePicker) view.findViewById(R.id.st);
        final DatePicker endTime = (DatePicker) view.findViewById(R.id.et);
        startTime.updateDate(startTime.getYear(), startTime.getMonth(), 01);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择时间");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int month = startTime.getMonth() + 1;
                String st1 = "" + startTime.getYear() +"年"+ month +"月"+ startTime.getDayOfMonth()+"日";
                String st = "" + startTime.getYear() +"-"+ month +"-"+ startTime.getDayOfMonth();
                start_time = st;
                // 将选择的时间显示到页面中
                activity_start_time.setText(st);

                int month1 = endTime.getMonth() + 1;
                String et1 = "" + endTime.getYear()+"年" + month1 +"月" + endTime.getDayOfMonth() +"日";
                String et = "" + endTime.getYear()+"-" + month1 +"-" + endTime.getDayOfMonth();

                end_time = et;
                activity_end_time.setText(et);
            }
        });

        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        //自动弹出键盘问题解决
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imagePath) {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        activityImage.setImageBitmap(bm);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}



