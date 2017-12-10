package com.example.soo.project;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

//현재 게시글 위치와 현재 내 위치를 보여주는 엑티비티이다.
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getReadableDatabase();
        Intent intent = getIntent();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = null;
        // Add a marker in Sydney and move the camera

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setCompassEnabled(true);
        mapSettings.setIndoorLevelPickerEnabled(true);
        mapSettings.setMapToolbarEnabled(true);
        mapSettings.setScrollGesturesEnabled(true);
        mapSettings.setTiltGesturesEnabled(true);
        mapSettings.setRotateGesturesEnabled(true);
        mapSettings.setMyLocationButtonEnabled(true);
        String[] projection1 = {FeedReaderContract.FeedEntry._ID,FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUE,FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE};
        Cursor c = db.query(FeedReaderContract.FeedEntry.TABLE_NAME_BORAD,projection1,null,null,null,null,"_id",null);
        if(c != null){
            if(c.moveToFirst()){
                do{
                    sydney = new LatLng(c.getDouble(2), c.getDouble(3));
                    mMap.addMarker(new MarkerOptions().position(sydney).title(c.getString(1)));

                }while (c.moveToNext());
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
        mMap.animateCamera(zoom);
    }
}
