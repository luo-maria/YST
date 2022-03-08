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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.yst.R;
import com.example.yst.adapter.ImageAdapter;
import com.example.yst.bean.ImageBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment1  extends Fragment {

    byte[] images;
    Bitmap imagebm;
    Intent intent;

    public static HomeFragment1 newInstance() {
        HomeFragment1 fragment = new HomeFragment1();
        return fragment;
    }
    private Banner mBanner;
    private List<ImageBean> mList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home0, container, false);
        mBanner = (Banner) view.findViewById(R.id.banner);
        ListView activitys = view.findViewById(R.id.activitys);
        Intent intent1=getActivity().getIntent();
        String username=intent1.getStringExtra("username");
        System.out.println("这里是HomeFragment1的username:"+username);
//        DatabaseHelper dbtest = new DatabaseHelper(HomeFragment1.this.getContext());
//        final SQLiteDatabase db = dbtest.getWritableDatabase();

        // 为列表项设置监听器
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//        Cursor cursor = db.rawQuery("select * from activity",null);
//        if (cursor.moveToFirst()){
//            while (!cursor.isAfterLast()){
//                item = new HashMap<String, Object>();  // 为列表项赋值
//                item.put("activity_id",cursor.getInt(0));
//                item.put("club_name",cursor.getString(2));
//                item.put("activity_name",cursor.getString(4));
//                item.put("activity_kind",cursor.getString(5));
//                item.put("activity_intro",cursor.getString(9));
//                images = cursor.getBlob(3);
//                imagebm = BitmapFactory.decodeByteArray(images, 0, images.length);
//                //kind1.setImageBitmap(imagebm);
//                item.put("activity_images",imagebm);
//                cursor.moveToNext();
//                data.add(item); // 加入到列表中
//            }
//
//        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(HomeFragment1.this.getContext(), data, R.layout.activity_lists, new String[] { "activity_images","club_name", "activity_name" ,"activity_intro"},
                new int[] { R.id.image,R.id.activity_create_name, R.id.activity_name,R.id.activity_intro  });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap( (Bitmap)data );
                    return true;
                }else{
                    return false;
                }
            }
        });
        activitys.setAdapter(simpleAdapter);
        // 为列表项设置监听器
//        activitys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                intent = new Intent(HomeFragment1.this.getActivity(), Activity_detail_Activity.class);
//                intent.putExtra("id", data.get(position).get("activity_id").toString());
//                //
//                intent.putExtra("username",username);
//                startActivity(intent);
//            }
//        });

        initView();
        initData();
        return view;
    }

    private void initView() {
        mBanner.setAdapter(new ImageAdapter(HomeFragment1.this.getActivity(), mList));
        // Set Banner is auto to loop.
        mBanner.isAutoLoop(true);
        // Set an indicator for Banner.
        mBanner.setIndicator(new CircleIndicator(this.getContext()));
        mBanner.start();
    }
    private void initData(){
        mList.add(new ImageBean(R.mipmap.a));
        mList.add(new ImageBean(R.mipmap.b));
        mList.add(new ImageBean(R.mipmap.xie));
        mList.add(new ImageBean(R.mipmap.tai));
        mList.add(new ImageBean(R.mipmap.wen));
    }

}

