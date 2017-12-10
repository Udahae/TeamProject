package com.example.soo.project;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Locationalarm extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    LocationReceiver mylocationreceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationalarm);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getApplicationContext(),"위치 변경 중",Toast.LENGTH_SHORT).show();
            }

            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            public void onProviderEnabled(String s) {
            }

            public void onProviderDisabled(String s) {
            }
        });
        mylocationreceiver = new LocationReceiver();
        IntentFilter filter = new IntentFilter("com.androidhuman.example.Location");
        registerReceiver(mylocationreceiver,filter);

        Intent intent1 = new Intent("com.androidhuman.example.Location");
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this,0,intent1,0);
        locationManager.addProximityAlert(37.222275,127.186292,100f,-1,proximityIntent);
    }
}
