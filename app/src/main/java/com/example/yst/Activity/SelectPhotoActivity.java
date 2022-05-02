package com.example.yst.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Student;
import com.example.yst.bean.UriEvent;
import com.example.yst.util.ConstantConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import org.greenrobot.eventbus.EventBus;
public class SelectPhotoActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "SelectPhotoActivity";
    private Uri imageUri;
    private static final int RESULT_CODE_STARTCAMERA = 0x01;
    public static final int TAKE_PHOTO = 0x02;
    public static final int CHOOSE_PHOTO = 0x03;
    public static final int CUT_PHOTO = 0x05;
    TextView text_take_photo,text_pick_photo,text_cancle;
    String getDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        Bmob.initialize(SelectPhotoActivity.this,"f84563e89fdb95cdc3c135df0c5ffc25");
        text_take_photo=findViewById(R.id.text_take_photo);
        text_pick_photo=findViewById(R.id.text_pick_photo);
        text_cancle=findViewById(R.id.text_cancle);

        text_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAlbum();
            }
        });
        text_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCamera();
            }
        });
        text_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
    @OnClick({R.id.text_take_photo, R.id.text_pick_photo, R.id.text_cancle})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_pick_photo:
                System.out.println("155555555511");
                onAlbum();
                break;
            case R.id.text_take_photo:
                onCamera();
                break;
            case R.id.text_cancle:
                finish();
                break;
        }
    }

    private void onAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);

    }

    private void onCamera() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            pickImageFromCamera();
        } else {
            //提示用户开户权限   拍照和读写sd卡权限
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, perms, RESULT_CODE_STARTCAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RESULT_CODE_STARTCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromCamera();
                } else {
                    Toast.makeText(SelectPhotoActivity.this,"您拒绝了权限许可",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //拍照
    private void pickImageFromCamera() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "output_image.jpg");//创建File对象，用于存储拍照后的图片，获取sd卡根目录
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);//File对象转化为Uri对象
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        System.out.println("shdhdfwehfimageuri"+imageUri);
        startActivityForResult(intent, TAKE_PHOTO); //启动相机程序
        System.out.println("11111111111imageuri"+imageUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getDate = getIntent().getStringExtra(ConstantConfig.SELECT_PHOTO);
        System.out.println("getDate is:"+getDate);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    switch (getDate) {
                        case ConstantConfig.UPDATE_HEAD_IMAGES:
                            cropPhoto(imageUri);
                            break;
                        case ConstantConfig.SELECT_CAMPUS_IMAGES:
                            EventBus.getDefault().post(new UriEvent(ConstantConfig.SELECT_CAMPUS_IMAGES, imageUri));
                            finish();
                            break;

                        default:
                    }
                    break;
                case CUT_PHOTO:
                    Bundle bundle = data.getExtras();
                    System.out.println("777777777777777777"+bundle );
                    if (bundle != null) {
                        //在这里获得了剪裁后的Bitmap对象，可以用于上传
                        Bitmap image = bundle.getParcelable("data");
                        String path = saveImage("Images", image);
                        System.out.println("path is :"+path);
                        setPicToView(path);
                    }
                    break;
                case CHOOSE_PHOTO:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    try {
                        Uri uri = data.getData();
                        switch (getDate) {
                            case ConstantConfig.UPDATE_HEAD_IMAGES:
                                cropPhoto(uri);
                                break;
                            case ConstantConfig.SELECT_CAMPUS_IMAGES:
                                EventBus.getDefault().post(new UriEvent(ConstantConfig.SELECT_CAMPUS_IMAGES, uri));
                                finish();
                                break;
                            default:
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    private void setPicToView(String path) {
        if (path == null) {
            Toast.makeText(SelectPhotoActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Student student =Student.getCurrentUser(Student.class);
//                    student.setPhotoImage(bmobFile);
                    System.out.println("333333333333");
                    student.update(student.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Toast.makeText(SelectPhotoActivity.this,"头像已上传",Toast.LENGTH_SHORT).show();
                            if (e == null) {
                                Toast.makeText(SelectPhotoActivity.this,"头像已上传成功",Toast.LENGTH_SHORT).show();
//                                EventBus.getDefault().post(new MessageEvent(ConstantConfig.HEAD_IMAGE_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(SelectPhotoActivity.this,"头像已上传失败",Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 1000);
        intent.putExtra("outputY", 1000);
        intent.putExtra("return-data", true);
        System.out.println("11111111122222");
        startActivityForResult(intent, CUT_PHOTO);
        System.out.println("2222222222222");
    }
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
