package com.example.sam.mvvm_demo.model;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.sam.mvvm_demo.bean.LocationEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sam on 10/22/17.
 */

public class LocationModel {
    private Context mContext;
    private LocationEntity mLocationEntity;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public LocationModel(Context mContext) {
        this.mContext = mContext;
        mLocationEntity = new LocationEntity();
    }

    public void requestLocate(final OnLocationModelListener listener) {
        locationManager=(LocationManager)((mContext).
                getSystemService(Context.LOCATION_SERVICE));
        String locationProvider;
        List<String> providers = locationManager.getProviders(true);
        if(providers.contains(LocationManager.NETWORK_PROVIDER)){

            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if(providers.contains(LocationManager.GPS_PROVIDER)){

            locationProvider = LocationManager.GPS_PROVIDER;
        }else{
            listener.fail(01,"No available GPS provider");
            return ;
        }
        locationListener = new LocationListener() {


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }


            @Override
            public void onProviderEnabled(String provider) {

            }


            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    String latitude;
                    if(location.getLatitude()>0){
                        latitude="N"+String.valueOf(location.getLatitude());
                    }else{
                        latitude="S"+String.valueOf(-location.getLatitude());
                    }
                    String longitude;
                    if(location.getLongitude()>0){
                        longitude="E"+String.valueOf(location.getLongitude());
                    }else{
                        longitude="W"+String.valueOf(-location.getLongitude());
                    }
                    mLocationEntity.setLatitude(latitude);
                    mLocationEntity.setLongitude(longitude);
                    SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd/   HH:mm:ss");
                    Date date=new Date(System.currentTimeMillis());
                    mLocationEntity.setDate(formatter.format(date));
                    listener.success(mLocationEntity);
                }
            }
        };

        try{
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                String latitude;
                if(location.getLatitude()>0){
                    latitude="N"+String.valueOf(location.getLatitude());
                }else{
                    latitude="S"+String.valueOf(-location.getLatitude());
                }
                String longitude;
                if(location.getLongitude()>0){
                    longitude="E"+String.valueOf(location.getLongitude());
                }else{
                    longitude="W"+String.valueOf(-location.getLongitude());
                }
                mLocationEntity.setLatitude(latitude);
                mLocationEntity.setLongitude(longitude);
                SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd   HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                mLocationEntity.setDate(formatter.format(date));
                listener.success(mLocationEntity);
            }

            locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        }catch (SecurityException e){
            listener.fail(02,e.getMessage());
        }catch(IllegalArgumentException e){
            listener.fail(03,e.getMessage());
        }
    }
    public interface OnLocationModelListener {
        void success(Object oj);
        void fail(int code,String message);
    }

}
