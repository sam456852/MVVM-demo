package com.example.sam.mvvm_demo.viewModel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableArrayMap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.example.sam.mvvm_demo.bean.LocationEntity;
import com.example.sam.mvvm_demo.model.LocationModel;
import com.example.sam.mvvm_demo.util.AppConfig;
import com.example.sam.mvvm_demo.view.MainActivity;

/**
 * Created by sam on 10/22/17.
 */

public class MainActivityViewModel {
    public ObservableArrayMap<String, Object> location = new ObservableArrayMap<>();
    private Context mContext;
    private LocationModel mLocationModel;

    public MainActivityViewModel(MainActivity mContext) {
        this.mContext = mContext;
        this.mLocationModel = new LocationModel(mContext.getApplicationContext());
        location.put("latitude", "Latitude");
        location.put("longitude", "Longitude");
        location.put("date", "Date");
    }

    public void search(View v) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt < Build.VERSION_CODES.M) {
            getLocate();
            return;
        }
        int permission = ContextCompat.checkSelfPermission
                (mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((MainActivity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    AppConfig.REQUEST_CODE);
        }else{
            getLocate();
        }
    }

    public void getLocate() {
        mLocationModel.requestLocate(new LocationModel.OnLocationModelListener() {
            @Override
            public void success(Object oj) {
                LocationEntity mLocationEntity=(LocationEntity)oj;
                location.put("latitude",mLocationEntity.getLatitude());
                location.put("longitude",mLocationEntity.getLongitude());
                location.put("date",mLocationEntity.getDate());
            }

            @Override
            public void fail(int code, String message) {
                showToast("Error code:"+String.valueOf(code)+"Error msg:"+message);
            }
        });
    }
    public void showToast(String text) {
        Toast.makeText(mContext.getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

}
