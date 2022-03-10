package com.example.yst.fragment;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


import com.example.yst.Activity.EditUserActivity;
import com.example.yst.R;
import com.example.yst.bean.Student;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobUser;

public class HomePageFragment extends Fragment {
    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }
    private byte[] image;
    ListView listView;
    ImageView club,message1;
    TextView my_club;
    LinearLayout myInfo;


    Bitmap imagebm;
    private String[] names = new String[]{"活动日程", "我的荣誉", "社团入驻",  "退出"};
    private int[] heads = new int[]{R.mipmap.cal};

    private ImageView portraittxt;
    private TextView my_name,signature;
    public void sendBroadcast(Intent intent) {
        Context mBase=HomePageFragment.this.getContext();
        mBase.sendBroadcast(intent);
    }
    /**
     * 退出登录
     */
    private void loginOut() {
        Intent intent = new Intent("com.gesoft.admin.loginout");
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建Fragment的布局
        final View view = inflater.inflate(R.layout.fragment_my, container, false);
        listView = view.findViewById(R.id.list_simple);
        club=view.findViewById(R.id.club);
        my_club=view.findViewById(R.id.my_club);
        portraittxt=view.findViewById(R.id.portraittxt);
        message1=view.findViewById(R.id.message1);
        myInfo=view.findViewById(R.id.myInfo);
        my_name =view.findViewById(R.id.my_name);
        signature=view.findViewById(R.id.signature);
        initView();
        return view;
    }
    private void initView() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        my_name.setText(userInfo.getNickname());
        signature.setText(userInfo.getSignature());
        List<Map<String, Object>> list = new ArrayList();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", names[i]);
            item.put("head", heads[0]);
            list.add(item);
        }

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomePageFragment.this.getActivity(), EditUserActivity.class);
                Intent intent=getActivity().getIntent();
                String Account =intent.getStringExtra("Account");
                intent1.putExtra("input_number",Account);
                System.out.println("这里是homepagefragment_number的number：" + Account);
                startActivity(intent1);
            }
        });

        portraittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(HomePageFragment.this.getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomePageFragment.this.getActivity(), new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
                    startActivityForResult(intent, 1);
                }
            }
        });
//        message1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(HomePageFragment.this.getActivity(), Apply_infoListActivity.class);
//                Intent intent=getActivity().getIntent();
//                String username=intent.getStringExtra("username");
//                intent2.putExtra("username",username);
//                startActivity(intent2);
//
//            }
//        });
//        club.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomePageFragment.this.getActivity(), MyclubsActivity.class);
//                Intent intent1=getActivity().getIntent();
//                String username=intent1.getStringExtra("username");
//                intent.putExtra("username",username);
//                startActivity(intent);
//
//            }
//        });
//        my_club.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomePageFragment.this.getActivity(), MyclubsActivity.class);
//                Intent intent1=getActivity().getIntent();
//                String username=intent1.getStringExtra("username");
//                intent.putExtra("username",username);
//                startActivity(intent);
//            }
//        });
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.item_simple,
                new String[]{"name", "head"},
                new int[]{R.id.tv_name, R.id.imageView1});
        listView.setAdapter(simpleAdapter);//绑定listView和simpleAdapter
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                toNewActivity(position);
//            }
//            private void toNewActivity(int position) {
//                Intent i;
//                switch (position) {
//                    case 0:
//                        i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
//                        startActivity(i);
//                        break;
//                    case 1:
//                        i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
//                        startActivity(i);
//                        break;
//                    case 2:
//                        i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
//                        Intent intent=getActivity().getIntent();
//                        String username=intent.getStringExtra("username");
//                        i.putExtra("username",username);
//                        startActivity(i);
//                        break;
//                    case 3:
//                        loginOut();
//                        break;
//                    default:
//                        i = new Intent(HomePageFragment.this.getActivity(), HomePageFragment.class);
//                        startActivity(i);
//                        break;
//                }
//
//            }
//        });
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
            c.close();
        }else if(data==null){
        }
    }
    public ContentResolver getContentResolver() {
        return HomePageFragment.this.getActivity().getContentResolver();
    }
    //加载图片
    private void showImage(String imaePath) {
        Intent intent=getActivity().getIntent();
        String username=intent.getStringExtra("Account");

        System.out.println("hpageusername+++++####"+username);
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        System.out.println("这是图片路径——+++++++++++++"+imaePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        image = baos.toByteArray();
        portraittxt.setImageBitmap(bm);

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