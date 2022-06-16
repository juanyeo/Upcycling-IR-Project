package com.example.differency.custom.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.example.differency.R;

import java.util.Random;

public class CustomActivity extends AppCompatActivity {

    Context context = this;

    ImageButton moldBagButton;
    ImageButton moldWalletButton;
    ImageButton moldShirtButton;

    ImageView moldBagView;
    ImageView moldWalletView;
    ImageView moldShirtView;

    AppCompatButton makeDesignButton;

    int imageCode = 0;
    int canvasNo = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_custom);

        Random random = new Random();
        canvasNo = random.nextInt(10);

        moldBagButton = (ImageButton) findViewById(R.id.mold_bag_button);
        moldWalletButton = (ImageButton) findViewById(R.id.mold_wallet_button);
        moldShirtButton = (ImageButton) findViewById(R.id.mold_shirt_button);

        moldBagView = (ImageView) findViewById(R.id.mold_bag);
        moldWalletView = (ImageView) findViewById(R.id.mold_wallet);
        moldShirtView = (ImageView) findViewById(R.id.mold_tshirt);

        makeDesignButton = (AppCompatButton) findViewById(R.id.make_design_button);

        //moldShirtView.setVisibility(View.VISIBLE);

        initImageView();
        setListeners();
    }

    private void initImageView() {
        ImageView canvasImage = (ImageView) findViewById(R.id.selected_canvas);
        Intent intent = getIntent();
        imageCode = intent.getIntExtra("ImageCode", 0);
        canvasImage.setImageResource(imageCode);

        TextView canvasNumber = (TextView) findViewById(R.id.canvas_number);
        canvasNumber.setText("Canvas-A120" + canvasNo);
    }

    private void setListeners() {
        moldBagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moldBagView.setVisibility(View.VISIBLE);
                moldWalletView.setVisibility(View.GONE);
                moldShirtView.setVisibility(View.GONE);
            }
        });

        moldWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moldBagView.setVisibility(View.GONE);
                moldWalletView.setVisibility(View.VISIBLE);
                moldShirtView.setVisibility(View.GONE);
            }
        });

        moldShirtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moldBagView.setVisibility(View.GONE);
                moldWalletView.setVisibility(View.GONE);
                moldShirtView.setVisibility(View.VISIBLE);
            }
        });

        makeDesignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ProductImageDialog(context, imageCode, canvasNo).show();
            }
        });
    }
}
