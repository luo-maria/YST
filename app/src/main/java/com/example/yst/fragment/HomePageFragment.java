package com.example.yst.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.yst.Activity.CalenderSelectActivity;
import com.example.yst.Activity.ClubUnionActivity;
import com.example.yst.Activity.CreateClubActivity;
import com.example.yst.Activity.EditUserActivity;
import com.example.yst.Activity.HomeActivity;
import com.example.yst.Activity.MyActivitiesActivity;
import com.example.yst.Activity.MyApplyListActivity;
import com.example.yst.Activity.MyHonorActivity;
import com.example.yst.Activity.MyclubsActivity;
import com.example.yst.R;
import com.example.yst.bean.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class HomePageFragment extends Fragment {
    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }
    private byte[] image;
    private ListView listView;
    private ImageView club,message1,imgHead;
    private TextView my_club;
    private LinearLayout myInfo,myclubs;
    private String img_url,stu_id;
    private String[] names = new String[]{"????????????","????????????", "????????????", "????????????",  "??????"};
    private int[] heads = new int[]{R.mipmap.schedule,R.mipmap.act,R.mipmap.honor,R.mipmap.settled,R.mipmap.exit_my};
    private TextView my_name,signature;

    public void sendBroadcast(Intent intent) {
        Context mBase=HomePageFragment.this.getContext();
        mBase.sendBroadcast(intent);
    }
    /**
     * ????????????
     */
    private void loginOut() {
        Intent intent = new Intent("com.gesoft.admin.loginout");
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //??????Fragment?????????
        final View view = inflater.inflate(R.layout.fragment_my, container, false);
        listView = view.findViewById(R.id.list_simple);
        club = view.findViewById(R.id.club);
        my_club = view.findViewById(R.id.my_club);
        message1 = view.findViewById(R.id.message1);
        myInfo = view.findViewById(R.id.myInfo);
        myclubs= view.findViewById(R.id. myclubs);
        my_name = view.findViewById(R.id.my_name);
        signature = view.findViewById(R.id.signature);
        imgHead = view.findViewById(R.id.img_profile);
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                String Account = intent.getStringExtra("Account");
                Intent intent1 = new Intent(HomePageFragment.this.getActivity(), EditUserActivity.class);
                intent1.putExtra("input_number", Account);
                startActivity(intent1);
            }
        });
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(HomePageFragment.this.getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomePageFragment.this.getActivity(), new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);//?????????????????????URI
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//?????????????????????URI
                    startActivityForResult(intent, 1);
                }
            }
        });
        message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomePageFragment.this.getActivity(), MyApplyListActivity.class);
                startActivity(intent1);
            }
        });
        initView();
        return view;
    }
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    private void initView() {
        Student student=Student.getCurrentUser(Student.class);
        stu_id=student.getObjectId();
        if(stu_id.equals("0DPfMMMa")){
            my_club.setText("????????????");
            my_club.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(HomePageFragment.this.getActivity(), ClubUnionActivity.class);
                    startActivity(intent);
                }
            });
        }
        List<Map<String, Object>> list = new ArrayList();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", names[i]);
            item.put("head", heads[i]);
            list.add(item);
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.item_my_simple,
                    new String[]{"name", "head"},
                    new int[]{R.id.tv_name, R.id.imageView1});
            listView.setAdapter(simpleAdapter);//??????listView???simpleAdapter
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    toNewActivity(position);
                }
                private void toNewActivity(int position) {
                    Intent i;
                    switch (position) {
                        case 0:
                            i = new Intent(HomePageFragment.this.getActivity(), CalenderSelectActivity.class);
                            startActivity(i);
                            break;
                        case 1:
                            i = new Intent(HomePageFragment.this.getActivity(), MyActivitiesActivity.class);
                            startActivity(i);
                            break;
                        case 2:
                            i = new Intent(HomePageFragment.this.getActivity(), MyHonorActivity.class);
                            startActivity(i);
                            break;
                        case 3:
                            i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
                            Intent intent=getActivity().getIntent();
                            String username=intent.getStringExtra("username");
                            i.putExtra("username",username);
                            startActivity(i);
                        case 4:
                            loginOut();
                            break;
                        default:
                            i = new Intent(HomePageFragment.this.getActivity(), HomePageFragment.class);
                            startActivity(i);
                            break;
                    }
                }
            });
            myclubs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(HomePageFragment.this.getActivity(), MyclubsActivity.class);
                    startActivity(intent1);
                }
            });

        }


        Student userInfo = BmobUser.getCurrentUser(Student.class);
        my_name.setText(userInfo.getNickname());
        signature.setText(userInfo.getSignature());
        if(userInfo.getPhotoimageurl()!=null){
            img_url=userInfo.getPhotoimageurl();
            Bitmap bm = BitmapFactory.decodeFile(img_url);
            System.out.println("??????????????????-------------"+img_url);
            imgHead.setImageBitmap(bm);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //??????????????????
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);

            c.close();
        }else if(data==null){
        }
    }
    public ContentResolver getContentResolver() {
        return HomePageFragment.this.getActivity().getContentResolver();
    }
    //????????????
    private void showImage(String imaePath) {
        imgHead.setImageBitmap(BitmapFactory.decodeFile(imaePath));
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        Student new_stu =new Student();
        new_stu.setPhotoimageurl(imaePath);
        new_stu.update(userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(HomePageFragment.this.getActivity(),"????????????",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(HomePageFragment.this.getActivity(), HomeActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(HomePageFragment.this.getActivity(),"????????????",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(HomePageFragment.this.getActivity(), HomeActivity.class);
                    startActivity(intent1);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    System.out.println("??????");
                }
                break;
            default:
        }
    }

}