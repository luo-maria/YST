package com.example.yst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.CalenderEvent;
import com.example.yst.bean.Student;
import com.example.yst.util.DateUtils;
import com.example.yst.util.EventDecorator;
import com.example.yst.util.FormatUtils;
import com.example.yst.util.StatusBarUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CalenderSelectActivity extends AppCompatActivity implements OnDateSelectedListener {
    private MaterialCalendarView widget;
    private EditText message_cal;
    private TextView mess_hold,mess_del;
    private String time,time1;
    private List<CalendarDay> calendarDays = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_select);
        initView();
        mess_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Student userInfo = BmobUser.getCurrentUser(Student.class);
                CalenderEvent calenderEvent=new CalenderEvent();
                calenderEvent.setMesstime(time1);
                calenderEvent.setTime(FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Time));
                calenderEvent.setMessage(message_cal.getText().toString());
                calenderEvent.setStud_id(userInfo.getObjectId());
                calenderEvent.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(CalenderSelectActivity.this,"添加日程成功!",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CalenderSelectActivity.this, CalenderSelectActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(CalenderSelectActivity.this,"添加日程失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mess_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void initView() {
        message_cal=findViewById(R.id.message_cal);
        mess_hold=findViewById(R.id.mess_hold);
        mess_del=findViewById(R.id.mess_del);
        widget=findViewById(R.id.materialCalendarView);
        StatusBarUtils.setTransparent(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        widget.setSelectedDate(CalendarDay.today());
        widget.setOnDateChangedListener(this);
        BmobQuery<CalenderEvent> signInBmobQuery = new BmobQuery<CalenderEvent>();
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        signInBmobQuery.addWhereEqualTo("stud_id", userInfo.getObjectId());
        signInBmobQuery.findObjects(new FindListener<CalenderEvent>() {
            @Override
            public void done(List<CalenderEvent> object, BmobException e) {
                if (e == null) {
                    if (!object.isEmpty()) {
                        for (CalenderEvent signIn : object) {
                            Date date = DateUtils.strToDate(signIn.getMesstime()+ " " + signIn.getTime());
                            calendarDays.add(CalendarDay.from(date));
                        }
                        widget.addDecorator(new EventDecorator(ContextCompat.getColor(CalenderSelectActivity.this, R.color.ss), calendarDays));
                    }
                } else {
                    Toast.makeText(CalenderSelectActivity.this,"显示数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        EventBus.getDefault().post(new CalenderEvent());
        time1=""+date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDay();
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        BmobQuery<CalenderEvent> bmobQuery = new BmobQuery<CalenderEvent>();
        bmobQuery.addWhereEqualTo("stud_id", userInfo.getObjectId());
        BmobQuery<CalenderEvent> bmobQuery1 = new BmobQuery<CalenderEvent>();
        bmobQuery1.addWhereEqualTo("messtime",time1);
        List<BmobQuery<CalenderEvent>> queries = new ArrayList<BmobQuery<CalenderEvent>>();
        queries.add(bmobQuery);
        queries.add(bmobQuery1);
        BmobQuery<CalenderEvent> query = new BmobQuery<CalenderEvent>();
        query.and(queries);
        query.findObjects(new FindListener<CalenderEvent>() {
            @Override
            public void done(List<CalenderEvent> list, BmobException e) {
                if(e==null){
                    if(!list.isEmpty()){
                        for(CalenderEvent cal:list){
                            message_cal.setText(cal.getMessage());
                            mess_del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CalenderEvent p2 = new CalenderEvent();
                                    p2.setObjectId(cal.getObjectId());
                                    p2.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                Toast.makeText(CalenderSelectActivity.this,"删除成功" ,Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(CalenderSelectActivity.this, CalenderSelectActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(CalenderSelectActivity.this,"删除失败" + e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }else {
                        message_cal.setText(time1+"：");
                    }
                }else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }

            }
        });

    }
}
