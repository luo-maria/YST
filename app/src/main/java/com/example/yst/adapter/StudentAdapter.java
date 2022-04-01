package com.example.yst.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter <StudentAdapter.MyViewHolder>{
    private List<Student> studentList;
    public StudentAdapter(List<Student> studentList){
        this.studentList=studentList;
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
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder holder, int position) {
        //设置显示的图片
        if(studentList.get(position).getPhotoimageurl()!=null){
            holder.grid_img.setImageBitmap(BitmapFactory.decodeFile(studentList.get(position).getPhotoimageurl()));
        }
        //设置显示的文字
        holder.grid_txt.setText(studentList.get(position).getRealname());
    }
    @Override
    public int getItemCount() {
        //设置显示的item数量为fruitList列表的元素的数量
        return studentList.size();
    }
}
