package com.example.soo.project;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean isOpen = false;
    private List<Photo> photos = null;
    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setPermissionStorage();
        Intent intent = getIntent();
        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getReadableDatabase();
        setPhotos();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        PhotosAdapter photosAdapter = new PhotosAdapter(this, photos);
        gridView.setAdapter(photosAdapter);
    }



    protected void onStart(){
        super.onStart();
        setPhotos();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        PhotosAdapter photosAdapter = new PhotosAdapter(this, photos);
        gridView.setAdapter(photosAdapter);
    }

    private void setPhotos() {
        photos = new ArrayList<Photo>();
        String[] projection1 = {FeedReaderContract.FeedEntry._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL, FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE};
        Cursor c = db.query(FeedReaderContract.FeedEntry.TABLE_NAME_BORAD,projection1,null,null,null,null,"_id",null);
        if(c != null){
            if(c.moveToFirst()){
                do{
                    photos.add(new Photo(c.getString(1),c.getString(2),c.getString(3)));
                }while (c.moveToNext());
            }
        }
        Toast.makeText(this,String.valueOf(photos.size()),Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.maps){
            // 여기에 액션을 취하기z
            Toast.makeText(getApplicationContext(),"MAPs",Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.insert){
            Intent intent = new Intent(this,CreateMemory.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


//    private Photo[] photos = {
//            new Photo(R.string.abc_an_amazing_alphabet_book, R.string.dr_seuss, R.drawable.abc,
//            "http://www.raywenderlich.com/wp-content/uploads/2016/03/abc.jpg"),
//            new Photo(R.string.are_you_my_mother, R.string.dr_seuss, R.drawable.areyoumymother,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/areyoumymother.jpg"),
//            new Photo(R.string.where_is_babys_belly_button, R.string.karen_katz, R.drawable.whereisbabysbellybutton,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/whereisbabysbellybutton.jpg"),
//            new Photo(R.string.on_the_night_you_were_born, R.string.nancy_tillman, R.drawable.onthenightyouwereborn,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/onthenightyouwereborn.jpg"),
//            new Photo(R.string.hand_hand_fingers_thumb, R.string.dr_seuss, R.drawable.handhandfingersthumb,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/handhandfingersthumb.jpg"),
//            new Photo(R.string.the_very_hungry_caterpillar, R.string.eric_carle, R.drawable.theveryhungrycaterpillar,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/theveryhungrycaterpillar.jpg"),
//            new Photo(R.string.the_going_to_bed_book, R.string.sandra_boynton, R.drawable.thegoingtobedbook,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/thegoingtobedbook.jpg"),
//            new Photo(R.string.oh_baby_go_baby, R.string.dr_seuss, R.drawable.ohbabygobaby,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/ohbabygobaby.jpg"),
//            new Photo(R.string.the_tooth_book, R.string.dr_seuss, R.drawable.thetoothbook,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/thetoothbook.jpg"),
//            new Photo(R.string.one_fish_two_fish_red_fish_blue_fish, R.string.dr_seuss, R.drawable.onefish,
//                    "http://www.raywenderlich.com/wp-content/uploads/2016/03/onefish.jpg")};

}
