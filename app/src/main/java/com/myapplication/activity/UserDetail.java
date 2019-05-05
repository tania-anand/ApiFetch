package com.myapplication.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.BuildConfig;
import com.myapplication.R;
import com.myapplication.model.User;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class UserDetail extends AppCompatActivity {

    @BindView(R.id.upload_photo)
    ImageView mImage;

    @BindView(R.id.txt_user_name)
    TextView mTxtUserName;

    @BindView(R.id.txt_location)
    TextView mTxtLocation;

    @BindView(R.id.txt_designation)
    TextView mTxtDesignation;

    @BindView(R.id.txt_salary)
    TextView mTxtSalary;

    User mUser;

    static final int CAMERA_REQUESTCODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        mUser = (User)getIntent().getSerializableExtra("user");
        setData();
        initView();

    }

    private void initView(){
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    startIntent();
                }else{
                    requestPermission();
                }


            }
        });
    }

    private void setData() {
        mTxtUserName.setText(mUser.getName());
        mTxtLocation.setText(mUser.getLocation());
        mTxtDesignation.setText(mUser.getDesignation());
        mTxtSalary.setText("$"+mUser.getSalary());
    }


    private static final int PERMISSION_CODE = 123;

    //Requesting permission
    private boolean checkPermission() {
        return  ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }




    private void startIntent(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUESTCODE);
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE },
                PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    startIntent();
                } else {
                    requestPermission();
                }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUESTCODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            mImage.setImageBitmap(bitmap);
        }
    }


}
