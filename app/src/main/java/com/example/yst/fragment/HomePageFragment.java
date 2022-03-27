package com.example.yst.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yst.Activity.CreateClubActivity;
import com.example.yst.Activity.EditUserActivity;
import com.example.yst.Activity.MyApplyListActivity;
import com.example.yst.Activity.MyclubsActivity;
import com.example.yst.Activity.SelectPhotoActivity;
import com.example.yst.R;
import com.example.yst.bean.Student;
import com.example.yst.util.ConstantConfig;
import com.example.yst.util.ImageUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
    LinearLayout myInfo,myclubs;
    String stu_id;
    private String[] names = new String[]{"活动日程", "我的荣誉", "社团入驻",  "退出"};
    private int[] heads = new int[]{R.mipmap.cal};
    private TextView my_name,signature;
//    @BindView(R.id.img_profile)
    SimpleDraweeView imgHead;

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
        Fresco.initialize(HomePageFragment.this.getContext());
        final View view = inflater.inflate(R.layout.fragment_my, container, false);
        listView = view.findViewById(R.id.list_simple);
        club = view.findViewById(R.id.club);
        my_club = view.findViewById(R.id.my_club);
        message1 = view.findViewById(R.id.message1);
        myInfo = view.findViewById(R.id.myInfo);
        myclubs= view.findViewById(R.id. myclubs);
        my_name = view.findViewById(R.id.my_name);
        signature = view.findViewById(R.id.signature);
        imgHead =(SimpleDraweeView) view.findViewById(R.id.img_profile);
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                String Account = intent.getStringExtra("Account");
                Intent intent1 = new Intent(HomePageFragment.this.getActivity(), EditUserActivity.class);
                intent1.putExtra("input_number", Account);
                System.out.println("这里是homepagefragment_number的number：" + Account);
                startActivity(intent1);
            }
        });
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantConfig.SELECT_PHOTO, ConstantConfig.UPDATE_HEAD_IMAGES);
                startActivity(SelectPhotoActivity.class, bundle);
            }
        });
        message1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Student userInfo = BmobUser.getCurrentUser(Student.class);
//                stu_id=userInfo.getObjectId();
                Intent intent1 = new Intent(HomePageFragment.this.getActivity(), MyApplyListActivity.class);
//                intent1.putExtra("stuid", stu_id);
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
        List<Map<String, Object>> list = new ArrayList();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", names[i]);
            item.put("head", heads[0]);
            list.add(item);
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), list, R.layout.item_simple,
                    new String[]{"name", "head"},
                    new int[]{R.id.tv_name, R.id.imageView1});
            listView.setAdapter(simpleAdapter);//绑定listView和simpleAdapter
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    toNewActivity(position);
                }
                private void toNewActivity(int position) {
                    Intent i;
                    switch (position) {
                        case 0:
                            i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
                            startActivity(i);
                            break;
                        case 1:
                            i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
                            startActivity(i);
                            break;
                        case 2:
                            i = new Intent(HomePageFragment.this.getActivity(), CreateClubActivity.class);
                            Intent intent=getActivity().getIntent();
                            String username=intent.getStringExtra("username");
                            i.putExtra("username",username);
                            startActivity(i);
                            break;
                        case 3:
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
        if (userInfo.getPhotoImage() == null) {
            return;
        }
        ImageUtils.setRoundImage(getContext(), imgHead, userInfo.getPhotoImage().getUrl());

    }


}