package com.example.differency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.differency.canvas.view.CanvasActivity;
import com.example.differency.custom.view.CustomActivity;
import com.example.differency.data.ImageData;
import com.example.differency.home.adapter.AIListAdapter;
import com.example.differency.home.view.CanvasImageDialog;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String dir_path;
    Uri contentUri;
    String [] permission_list = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        requestPermissionsByVersion();

        setAiListRecyclerAdapter();
        setLikeRecyclerAdapter();
    }

    public void getImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 3);
    }

    public void requestPermissionsByVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int a1 : grantResults) {
            if(a1 == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        init();
    }

    public void init() {
        // 내장 메모리 주소 + /Android/data/ + 패키지 명
        String exdir_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        dir_path = exdir_path + "/Android/data/" + getPackageName();
        File file = new File(dir_path);
        if(file.exists() == false) {
            file.mkdir();
        }
    }

    private void setAiListRecyclerAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ai_list_rv);
        AIListAdapter adapter = new AIListAdapter(ImageData.MAIN_IMAGE, new AIListAdapter.OnActionListener() {
            @Override
            public void onImageClick(View view, int imageCode) {
                Intent intent = new Intent();
                intent.putExtra("ImageCode", imageCode);
                intent.setClassName(getApplicationContext(), CustomActivity.class.getName());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setLikeRecyclerAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.like_list_rv);
        AIListAdapter adapter = new AIListAdapter(ImageData.MY_IMAGE, new AIListAdapter.OnActionListener() {
            @Override
            public void onImageClick(View view, int imageCode) {
                Intent intent = new Intent();
                intent.putExtra("ImageCode", imageCode);
                intent.setClassName(getApplicationContext(), CustomActivity.class.getName());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 3:
                    // Uri 객체와 Content Resolver를 통해 이미지의 정보를 가져온다
                    Uri uri = data.getData();
                    ContentResolver resolver = getContentResolver();
                    Cursor cursor = resolver.query(uri, null, null, null, null);
                    cursor.moveToNext();

                    // 이미지의 정보 중 이미지의 경로를 추출한다
                    int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    String source = cursor.getString(index);

                    // 이미지 경로로 이미지 객체를 생성한다
                    Bitmap bitmap5 = BitmapFactory.decodeFile(source);

                    Bitmap bitmap6 = resizeBitmap(1024, bitmap5);
                    float degree2 = getDegree2(source);
                    Bitmap bitmap7 = rotateBitmap(bitmap6, degree2);

                    new CanvasImageDialog(this, bitmap7, new CanvasImageDialog.OnActionListener() {
                        @Override
                        public void openCanvasList() {
                            Intent intent = new Intent();
                            intent.putExtra("ImageCode", 5);
                            intent.setClassName(getApplicationContext(), CanvasActivity.class.getName());
                            startActivity(intent);
                        }
                    }).show();

                    break;
            }
        }
    }

    public Bitmap resizeBitmap(int targetWidth, Bitmap bitmap) {
        double ratio = (double) targetWidth / (double) bitmap.getWidth();
        int targetHeight = (int)(bitmap.getHeight() * ratio);
        Bitmap result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
        if(result != bitmap) {
            // 원본 이미지 객체를 소멸시킨다
            bitmap.recycle();
        }
        return result;
    }

    public float getDegree() {
        try{
            // 이미지 파일에 저장된 정보를 가져온다
            ExifInterface exif = new ExifInterface(contentUri.getPath());
            int degree = 0;
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch(ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            return (float) degree;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public float getDegree2(String source) {
        try{
            // 이미지 파일에 저장된 정보를 가져온다
            ExifInterface exif = new ExifInterface(source);
            int degree = 0;
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch(ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            return (float) degree;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Bitmap rotateBitmap(Bitmap bitmap, float degree) {
        try{
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.postRotate(degree);

            Bitmap result = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
            bitmap.recycle();
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}