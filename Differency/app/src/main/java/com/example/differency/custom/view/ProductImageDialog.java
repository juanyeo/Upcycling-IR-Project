package com.example.differency.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.differency.R;
import com.example.differency.home.view.CanvasImageDialog;

public class ProductImageDialog extends Dialog {

    private View view;
    private ImageView imageView;
    private TextView contentTextView;
    private AppCompatButton orderButton;

    public ProductImageDialog(Context context, int imageCode, int canvasNo) {
        super(context);

        view = getLayoutInflater().inflate(R.layout.dialog_designed_product, null);

        imageView = (ImageView) view.findViewById(R.id.inside_image_view);
        imageView.setImageResource(imageCode);

        contentTextView = (TextView) view.findViewById(R.id.product_content);

        String contentString = "canvas-a120" + canvasNo + " + tote bag";
        contentTextView.setText(contentString);

        orderButton = (AppCompatButton) view.findViewById(R.id.order_btn);

        setEventListener();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.shape_dialog_background));

        setCancelable(true);
        setContentView(view);
    }

    private void setEventListener() {
        // 데모를 위해 이미지뷰, 버튼에 같은 리스너 추가
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
