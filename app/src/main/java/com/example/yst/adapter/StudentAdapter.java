package com.example.yst.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter <StudentAdapter.MyViewHolder>implements View.OnClickListener{
    private List<Student> studentList;
    public Context mcontext;

    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));

    }

    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);
        void onItemLongClick(View view , int pos);

    }
    public StudentAdapter(Context context, List<Student> studentList, StudentAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.studentList=studentList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView grid_img;
        private TextView grid_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_img = itemView.findViewById(R.id.studentPhoto);
            grid_txt = itemView.findViewById(R.id.studentName);
        }
    }
    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder holder, int position) {
        //设置显示的图片
        if(!studentList.get(position).getPhotoimageurl().equals("")){
            holder.grid_img.setImageBitmap(BitmapFactory.decodeFile(studentList.get(position).getPhotoimageurl()));
        }else{
            holder.grid_img.setImageResource(R.mipmap.poy);
        }
        //设置显示的文字
        holder.grid_txt.setText(studentList.get(position).getRealname());
        holder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnRecyclerviewItemClickListener.onItemLongClick(holder.itemView,position);
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        //设置显示的item数量为fruitList列表的元素的数量
        return studentList.size();
    }

}
