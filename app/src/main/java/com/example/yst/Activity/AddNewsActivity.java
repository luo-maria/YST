package com.example.yst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.News;
import com.example.yst.bean.Student;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class AddNewsActivity extends AppCompatActivity {
    private ImageView my_image,news_image;
    private TextView edit_man;
    private EditText news_title,news_content;
    private LinearLayout upload;
    private Button release;
    private String club_id,stu_id,stu_name,new_image,club_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);
        initView();
        news_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddNewsActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddNewsActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
                    startActivityForResult(intent, 1);
                }
            }
        });

        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news=new News();
                news.setClub_id(club_id);
                news.setNews_image(new_image);
                news.setNews_title(news_title.getText().toString());
                news.setNews_content(news_content.getText().toString());
                news.setClub_name(club_name);
                news.setHeat(0);
                news.setLikes(0);
                news.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(AddNewsActivity.this,"发布资讯成功！",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("发布资讯失败","原因："+e);
                        }
                    }
                });
                Intent intent=new Intent(AddNewsActivity.this, ManageClubActivity.class);
                intent.putExtra("clubid",club_id);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        my_image=findViewById(R.id.my_image1);
        edit_man=findViewById(R.id.edit_man1);
        news_title=findViewById(R.id.news_title1);
        news_content=findViewById(R.id.news_content1);
        news_image=findViewById(R.id.news_image);
        release=findViewById(R.id.release1);
        Intent intent1=getIntent();
        club_id=intent1.getStringExtra("clubid");
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        BmobQuery<Student> studentQuery = new BmobQuery<Student>();
        studentQuery.getObject(stu_id, new QueryListener<Student>() {
            @Override
            public void done(Student student, BmobException e) {
                if(e==null){
                    if(student.getPhotoimageurl()!=null){
                        my_image.setImageBitmap(BitmapFactory.decodeFile(student.getPhotoimageurl()));
                        stu_name=student.getNickname();
                        edit_man.setText(stu_name);
                    }else{
                        my_image.setImageResource(R.mipmap.portrait);
                    }
                }else{
                    Log.e("查询失败2","原因："+e);
                }
            }
        });
        // 查询当前社团
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id, new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
                if(e==null){
                    club_name=object.getClub_name();
                }else{
                    Toast.makeText(AddNewsActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            new_image=imagePath;
            c.close();
        }else if(data==null){
        }
    }
    //加载图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        news_image.setImageBitmap(bm);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    System.out.println("错误");
                }
                break;
            default:
        }
    }




}
