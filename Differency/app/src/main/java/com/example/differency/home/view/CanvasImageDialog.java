package com.example.differency.home.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.differency.R;

public class CanvasImageDialog extends Dialog {

    public interface OnActionListener {
        void openCanvasList();
    }

    private View view;
    private ImageView imageView;
    private AppCompatButton searchButton;
    private Bitmap albumImageBitmap;

    private OnActionListener listener;

    public CanvasImageDialog(Context context, Bitmap albumImageBitmap, OnActionListener listener) {
        super(context);
        this.albumImageBitmap = albumImageBitmap;
        this.listener = listener;

        view = getLayoutInflater().inflate(R.layout.dialog_canvas_image, null);

        imageView = (ImageView) view.findViewById(R.id.album_image_view);
        imageView.setImageBitmap(albumImageBitmap);

        searchButton = (AppCompatButton) view.findViewById(R.id.ai_search_btn);

        setEventListener();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.shape_dialog_background));

        setCancelable(true);
        setContentView(view);
    }

    private void setEventListener() {
        // 데모를 위해 이미지뷰, 버튼에 같은 리스너 추가
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openCanvasList();
                dismiss();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openCanvasList();
                dismiss();
            }
        });
    }
}
