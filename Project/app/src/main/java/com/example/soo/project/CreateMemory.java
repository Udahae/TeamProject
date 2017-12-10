package com.example.soo.project;

import android.*;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class CreateMemory extends AppCompatActivity {
    GoogleApiClient googleApiClient;
    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;
    FloatingActionButton fab, fab2;
    Animation fabOpen,fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private final static int GALLERY_CODE = 2222;
    long minTime = 10000;
    float minDistance = 100;
    Uri imageUri = null;
    Bitmap bitmap = null;
    Double latitude = null;
    Double longitude = null;
    String currentLocationAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__memory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getWritableDatabase();
        setFab();
    }

    private void setFab(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });
        //앨범 버튼
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),GALLERY_CODE);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_memory_menu,menu);
        return true;
    }
    protected void onStart(){
        super.onStart();
        if(googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).build();
        }
        googleApiClient.connect();
    }
    // Intent의 결과를 받아올때
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    imageUri = data.getData();
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                        String seletedUri = getPath(imageUri);
                        ImageView imageView1 = (ImageView)findViewById(R.id.imageView);
//                        imageView1.setImageBitmap(bitmap);
                        imageView1.setImageURI(Uri.parse("file://"+seletedUri));
                        imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    break;
            }
        }
    }

    private String getPath(Uri imgUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(imgUri,projection,null,null,null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return imgUri.getPath();
    }

    //애니메이션 효과 함수
    private void animateFab(){
        if(isOpen){
            fab.startAnimation(rotateForward);
            fab2.startAnimation(fabClose);
            fab2.setClickable(false);
            isOpen = false;
        }else{
            fab.startAnimation(rotateBackward);
            fab2.startAnimation(fabOpen);
            fab2.setClickable(true);
            isOpen = true;
        }
    }

    //GPS버튼을 눌렀을 경우 실행하는 함수
    public void startLocation(View view) {
        String[] permissions = new String[]{android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(String permission:permissions){
                int result = PermissionChecker.checkSelfPermission(this, permission);
                if(result == PermissionChecker.PERMISSION_GRANTED);
                else{
                    ActivityCompat.requestPermissions(this,permissions,1);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        long minTime = 10000;
        float minDistance = 100;
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //위도와 경도의 변화하는 값을 계속 받을 수 있다.
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                getAddress(CreateMemory.this,latitude,longitude);
            }
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            public void onProviderEnabled(String s) {}
            public void onProviderDisabled(String s) {}
        });

//        Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
    }
    //  위도 경도를 주소로 변경하는 함수
    public void getAddress(Context mContext, double lat, double lng) {
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);
                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    EditText el = (EditText)findViewById(R.id.location);
                    el.setText(currentLocationAddress);
                }
            }

        } catch (IOException e) {
            Toast.makeText(CreateMemory.this, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.updatae){
            // 여기에 액션을 취하기

            savedata();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savedata(){
        EditText title = (EditText)findViewById(R.id.title);
        EditText content = (EditText)findViewById(R.id.content);
        String t = title.getText().toString();
        String c = title.getText().toString();
//        String i = String.valueOf(imageUri);
        String i = getPath(imageUri);
        ContentValues v = new ContentValues();
        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL,((LoginActivity)LoginActivity.context).getEmail());
//        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_GROUP,"all");
        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,t);
        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT,c);
        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE,i);
        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUE,latitude);
        v.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE,longitude);
        long newid = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME_BORAD,null,v);
        Toast.makeText(getApplicationContext(),String.valueOf(newid),Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
        finish();
    }

}
