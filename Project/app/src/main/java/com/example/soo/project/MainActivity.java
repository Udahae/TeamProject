package com.example.soo.project;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    boolean music = false;
    private List<Photo> photos = null;
    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;


    @Override

    //메인 화면에 액션을 취하는 부분이다.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getReadableDatabase();
        setPhotos();
        GridView gridView = (GridView) findViewById(R.id.gridview);
        PhotosAdapter photosAdapter = new PhotosAdapter(this, photos);
        gridView.setAdapter(photosAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("num", photos.get(position).getNumber());
                startActivity(intent);
            }
        });


    }

    protected void onStart(){
        super.onStart();
        setPhotos();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        PhotosAdapter photosAdapter = new PhotosAdapter(this, photos);
        gridView.setAdapter(photosAdapter);
    }
// 현재 가지고 있는 게시글을 List로 만드는 것이다.
    private void setPhotos() {
        photos = new ArrayList<Photo>();
        String[] projection1 = {FeedReaderContract.FeedEntry._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL, FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE};
        Cursor c = db.query(FeedReaderContract.FeedEntry.TABLE_NAME_BORAD,projection1,null,null,null,null,"_id",null);
        if(c != null){
            if(c.moveToFirst()){
                do{
                    photos.add(new Photo(Integer.parseInt(c.getString(0)),c.getString(1),c.getString(2),c.getString(3)));
                }while (c.moveToNext());
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
// 메인 상단에 있는 메뉴에 있는 아이콘의 역할을 할당하는 부분이다.
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.maps){
            // 여기에 액션을 취하기
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"MAPs",Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.insert){
            Intent intent = new Intent(this,CreateMemory.class);
            startActivity(intent);
        }else if(id == R.id.music){
            if(music == false){
                item.setIcon(R.drawable.ic_pause_black_24dp);
                music = true;
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
            }else if(music == true){
                item.setIcon(R.drawable.ic_play_arrow_black_24dp);
                music = false;
                Intent intent = new Intent(this, MyService.class);
                stopService(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
