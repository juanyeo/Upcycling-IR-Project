package com.example.differency.canvas.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.differency.R;
import com.example.differency.canvas.adapter.CanvasListAdapter;
import com.example.differency.custom.view.CustomActivity;
import com.example.differency.data.ImageData;
import com.example.differency.home.adapter.AIListAdapter;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_canvas);

        setImageListRecyclerAdapter();
    }

    private void setImageListRecyclerAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.canvas_list_rv);
        CanvasListAdapter adapter = new CanvasListAdapter(getApplicationContext(), ImageData.CANVAS_IMAGE, new CanvasListAdapter.OnActionListener() {
            @Override
            public void onImageClick(View view, int imageCode) {
                Intent intent = new Intent();
                intent.putExtra("ImageCode", imageCode);
                intent.setClassName(getApplicationContext(), CustomActivity.class.getName());
                startActivity(intent);
            }
        });
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
