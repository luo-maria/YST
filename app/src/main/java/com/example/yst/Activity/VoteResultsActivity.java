package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.yst.R;
import com.example.yst.bean.Vote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static java.util.Map.Entry.comparingByValue;

public class VoteResultsActivity extends BaseActivity {
    private String vote_id,vote_option1,vote_option2,vote_option3;
    private Integer vote_option1num,vote_option2num,vote_option3num,sum;
    private TextView rnews_title,rnews_content,top_one,top_two,top_three,total_number,
            firstnumber,sencondnumber,thirdnumber,endend;
    private List<String> num=new ArrayList<>();
    private List<Integer> num1=new ArrayList<>();
    HashMap <String, Integer> budget = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_results);
        initialize();
    }

    private void initialize() {
        Intent intent1=getIntent();
        vote_id=intent1.getStringExtra("voteid");
        rnews_title=findViewById(R.id.rnews_title);
        rnews_content=findViewById(R.id.rnews_content);
        top_one=findViewById(R.id.top_one);
        top_two=findViewById(R.id.top_two);
        top_three=findViewById(R.id.top_three);
        total_number=findViewById(R.id.total_number);
        firstnumber=findViewById(R.id.firstnumber);
        sencondnumber=findViewById(R.id.sencondnumber);
        thirdnumber=findViewById(R.id.thirdnumber);
        endend=findViewById(R.id.endend);
        BmobQuery<Vote> bmobQuery = new BmobQuery<Vote>();
        bmobQuery.getObject(vote_id, new QueryListener<Vote>() {
            @Override
            public void done(Vote vote, BmobException e) {
                if(e==null){
                    rnews_title.setText(vote.getHeadline());
                    rnews_content.setText(vote.getIntroduction());
                    vote_option1=vote.getOption1();
                    vote_option2=vote.getOption2();
                    vote_option3=vote.getOption3();
                    vote_option1num=vote.getOption1_num();
                    vote_option2num=vote.getOption2_num();
                    vote_option3num=vote.getOption3_num();
                    endend.setText(vote.getVoteEnd().getDate());
                    sortValue();
                }else{
                    Log.e("查询失败！","原因",e);
                }
            }
        });

    }

    private void sortValue() {
        Map<String, Integer> keyfreqs = new HashMap<String, Integer>();
        keyfreqs.put(vote_option1,vote_option1num);
        keyfreqs.put(vote_option2,vote_option2num);
        keyfreqs.put(vote_option3,vote_option3num);
        ArrayList<Map.Entry<String,Integer>> l = new ArrayList<Map.Entry<String,Integer>>(keyfreqs.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        sum=0;
        for(Map.Entry<String,Integer> e : l) {
            num.add(e.getKey());
            num1.add(e.getValue());
            sum=sum+e.getValue();
        }
        top_one.setText(num.get(0));
        top_two.setText(num.get(1));
        top_three.setText(num.get(2));
        firstnumber.setText(String.valueOf(num1.get(0)));
        sencondnumber.setText(String.valueOf(num1.get(1)));
        thirdnumber.setText(String.valueOf(num1.get(2)));
        total_number.setText(String.valueOf(sum));
    }

}
