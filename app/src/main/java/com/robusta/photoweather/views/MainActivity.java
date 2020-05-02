package com.robusta.photoweather.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.robusta.photoweather.R;
import com.robusta.photoweather.databinding.ActivityMainBinding;
import com.robusta.photoweather.entities.ApiResponse;
import com.robusta.photoweather.entities.WeatherApiObject;
import com.robusta.photoweather.entities.WeatherPhoto;
import com.robusta.photoweather.helpers.AlertDialogHelper;
import com.robusta.photoweather.helpers.Utils;
import com.robusta.photoweather.viewmodels.HistoryViewModel;
import com.robusta.photoweather.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int MY_LOCATION_PERMISSION_CODE = 101;
    ActivityMainBinding activityMainBinding;
    LocationManager locManager;
    private MainViewModel viewModel;
    private WeatherPhoto weatherPhoto;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            //updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            //updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Bitmap bitmap = getIntent().getParcelableExtra("image");

        activityMainBinding.capturedImage.setImageBitmap(bitmap);

        weatherPhoto = new WeatherPhoto(Utils.bitmapsToBytes(bitmap), null);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_LOCATION_PERMISSION_CODE);
            } else {

                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,
                        500.0f, locationListener);
                Location location = locManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //Toast.makeText(this, "location, lon :"+longitude + " , lat: "+latitude, Toast.LENGTH_LONG).show();

                    getWeatherInfo(longitude, latitude);
                }
            }
        }

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(Utils.bytesToBitmaps(weatherPhoto.getImage()))
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareButton shareButton = findViewById(R.id.shareFb);
        shareButton.setShareContent(content);


    }

    private void getWeatherInfo(double lon, double lat) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);
        viewModel.getWeatherInfo(lon, lat).observe(this, new Observer<ApiResponse<WeatherApiObject>>() {
            @Override
            public void onChanged(ApiResponse<WeatherApiObject> weatherApiObjectApiResponse) {
                dialog.dismiss();

                if (weatherApiObjectApiResponse.getStatus() == 200){

                    Log.e("TAG", "onChanged: " + weatherApiObjectApiResponse.getBody().getName() );
                    Log.e("TAG", "onChanged: " + weatherApiObjectApiResponse.getBody().getMain().getTemp() );
                    weatherPhoto.setWeatherInfo(weatherApiObjectApiResponse.getBody());
                    activityMainBinding.setWeatherInfo(weatherApiObjectApiResponse.getBody());
                }else{
                    AlertDialogHelper.showAlert(MainActivity.this, "Warning", "Something went wrong!", "Ok");
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,
                        500.0f, locationListener);
                Location location = locManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    getWeatherInfo(longitude, latitude);
                }
            }
            else
            {
                Toast.makeText(this, "location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void save(View view) {
        viewModel.insertWeatherInfo(weatherPhoto);
        finish();
    }

    public void shareFb(View view) {

    }
}
