package com.example.differency.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.differency.R;
import com.example.differency.canvas.adapter.CanvasListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AIListAdapter extends RecyclerView.Adapter<AIListAdapter.CanvasViewHolder> {

    public interface OnActionListener {
        void onImageClick(View view, int imageCode);
    }

    List<Integer> imageList = new ArrayList<>();

    private AIListAdapter.OnActionListener listener;

    public AIListAdapter(List<Integer> imageList, AIListAdapter.OnActionListener listener) {
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CanvasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.renderer_main_image, parent, false);
        return new CanvasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanvasViewHolder holder, int position) {
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

        public void bind(int imageId) {
            ImageView imageView = (ImageView) itemView.findViewById(R.id.canvas_image);
            imageView.setImageResource(imageId);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(view, imageId);
                }
            });
        }
    }
}
