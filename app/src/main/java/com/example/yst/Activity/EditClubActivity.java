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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.util.ConstantConfig;

import java.io.ByteArrayOutputStream;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditClubActivity extends BaseActivity {
    private EditText clubname_edit,leadername_edit,leadercall_edit,clubintro_edit;
    private Spinner sp,sp1,sp2;
    private Button create_edit;
    private String club_id6,level_edit,campus_edit,kind_edit,imagePath;
    private ImageView logo_edit,backPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_club);
        initView();
        backPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditClubActivity.this,ManageClubActivity.class);
                intent.putExtra("clubid",club_id6);
                startActivity(intent);
            }
        });

    }

    private void setMyClub() {
        Intent intent1=getIntent();
        club_id6=intent1.getStringExtra("clubid");
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id6, new QueryListener<Club>() {
            @Override
            public void done(Club club, BmobException e) {
                if(e==null ){
                    if (club.getClub_name()!= null) {
                       clubname_edit.setText(club.getClub_name());
                    }
                    if (club.getClub_intro()!= null) {
                        clubintro_edit.setText(club.getClub_intro());
                    }
                    if (club.getClub_president()!= null) {
                        leadername_edit.setText(club.getClub_president());
                    }
                    if (club.getPre_number()!= null) {
                        leadercall_edit.setText(club.getPre_number());
                    }
                    if (club.getLogo_url()!= null) {
                        logo_edit.setImageBitmap(BitmapFactory.decodeFile(club.getLogo_url()));
                    }

                }else{
                    Log.e("????????????","?????????",e);                }
            }
        });
    }

    private void initView() {
        backPre=findViewById(R.id.backPre);
        clubname_edit=findViewById(R.id.clubname_edit);
        leadername_edit=findViewById(R.id.leadername_edit);
        leadercall_edit=findViewById(R.id.leadercall_edit);
        clubintro_edit=findViewById(R.id.clubintro_edit);
        create_edit=findViewById(R.id.create_edit);
        logo_edit=findViewById(R.id.clublogo_edit);
        setMyClub();
        sp1 = (Spinner) super.findViewById(R.id.campus_edit);
        String[] ctype = new String[]{"????????????", "????????????","????????????","????????????"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //???????????????
                Spinner spinner1 = (Spinner) adapterView;
                campus_edit=spinner1.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp2 = (Spinner) super.findViewById(R.id.kind_edit);
        String[] ktype = new String[]{"??????", "??????","??????","??????"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ktype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //???????????????
                Spinner spinner1 = (Spinner) adapterView;
                kind_edit=spinner1.getItemAtPosition(position).toString();
//                System.out.println("??????????????????:"+level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        logo_edit.setOnClickListener(new View.OnClickListener() {
            private Object SelectPhotoActivity;
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EditClubActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditClubActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //??????????????????
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);

            }}
        });
        create_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club thisclub=new Club();
                thisclub.setClub_name(clubname_edit.getText().toString());
                thisclub.setClub_intro(clubintro_edit.getText().toString());
                thisclub.setPre_number(leadercall_edit.getText().toString());
                thisclub.setClub_president(leadername_edit.getText().toString());
                thisclub.setClub_category(kind_edit);
//                thisclub.setClub_rank(level_edit);
                thisclub.setClub_campus(campus_edit);
                thisclub.setLogo_url(imagePath);
                thisclub.update(club_id6, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(EditClubActivity.this,"????????????:"+clubname_edit.getText().toString(),Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EditClubActivity.this, ManageClubActivity.class);
                           intent1.putExtra("clubid",club_id6);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(EditClubActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //??????????????????
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //????????????
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        logo_edit.setImageBitmap(bm);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
