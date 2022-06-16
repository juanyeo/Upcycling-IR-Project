package com.example.differency.canvas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.differency.R;
import com.example.differency.canvas.view.CanvasActivity;
import com.example.differency.custom.view.CustomActivity;
import com.example.differency.home.adapter.AIListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CanvasListAdapter extends RecyclerView.Adapter<CanvasListAdapter.CanvasViewHolder> {

    public interface OnActionListener {
        void onImageClick(View view, int imageCode);
    }

    Context context;
    List<Integer> imageList = new ArrayList<>();

    private OnActionListener listener;

    public CanvasListAdapter(Context context, List<Integer> imageList, OnActionListener listener) {
        this.context = context;
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CanvasListAdapter.CanvasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.renderer_canvas_image, parent, false);
        return new CanvasListAdapter.CanvasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanvasListAdapter.CanvasViewHolder holder, int position) {
        holder.bind(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class CanvasViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public CanvasViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bind(int imageCode) {
            ImageView imageView = (ImageView) itemView.findViewById(R.id.canvas_image);

            imageView.setImageResource(imageCode);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(view, imageCode);
                }
            });
        }
    }
}
