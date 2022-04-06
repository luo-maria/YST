package com.example.yst.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yst.R;


public class HonorDialog extends Dialog {
    private String name,number,kind;
    private TextView honor_name,honor_number,honor_kind,text_pos;
    private View.OnClickListener confirmClickListener;
    public HonorDialog(Context context, String name, String number, String kind, View.OnClickListener confirmClickListener) {
        super(context, R.style.Dialog);
        this.name = name;
        this.number = number;
        this.kind=kind;
        this.confirmClickListener=confirmClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honor_popup);
        honor_name=findViewById(R.id.honor_name);
        honor_number=findViewById(R.id.honornum);
        honor_kind=findViewById(R.id.honor_kind);
        text_pos=findViewById(R.id.text_pos);
        honor_name.setText(name);
        honor_kind.setText(kind);
        honor_number.setText(number);
        text_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               HonorDialog.this.dismiss();
            }
        });
    }
}
