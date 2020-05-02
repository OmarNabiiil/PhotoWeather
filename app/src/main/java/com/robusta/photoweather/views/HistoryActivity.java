package com.robusta.photoweather.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.robusta.photoweather.R;
import com.robusta.photoweather.adapters.HistoryAdapter;
import com.robusta.photoweather.entities.WeatherPhoto;
import com.robusta.photoweather.helpers.Utils;
import com.robusta.photoweather.viewmodels.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.ItemClickListener {

    private List<WeatherPhoto> weatherPhotoList;
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    private HistoryViewModel viewModel;
    private TextView emptyState;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerview);
        emptyState = findViewById(R.id.emptyState);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        weatherPhotoList = new ArrayList<>();
        mAdapter = new HistoryAdapter(weatherPhotoList, this, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllHistory();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("image", photo);
            startActivity(intent);
        }
    }

    public void capture(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
    }

    private void getAllHistory() {
        viewModel.getAllHistory().observe(this, new Observer<List<WeatherPhoto>>() {
            @Override
            public void onChanged(List<WeatherPhoto> weatherPhotos) {
                if (weatherPhotos == null || weatherPhotos.isEmpty()){
                    emptyState.setVisibility(View.VISIBLE);
                }else{
                    emptyState.setVisibility(View.GONE);
                    weatherPhotoList.clear();
                    weatherPhotoList.addAll(weatherPhotos);

                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onWeatherPhotoClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        WeatherPhoto weatherPhoto = weatherPhotoList.get(itemPosition);

        showImageFullScreen(weatherPhoto.getImage());
    }

    public void showImageFullScreen(byte[] image) {
        final Dialog dialog = new Dialog(HistoryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_image_preview);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView imageView = dialog.findViewById(R.id.image);
        imageView.setImageBitmap(Utils.bytesToBitmaps(image));

        (dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
