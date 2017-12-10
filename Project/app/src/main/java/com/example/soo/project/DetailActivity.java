package com.example.soo.project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    FeedReaderDBOpenHelper openHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        openHelper = new FeedReaderDBOpenHelper(this);
        db = openHelper.getReadableDatabase();
        Intent intent = getIntent();
        String[] projection1 = {FeedReaderContract.FeedEntry._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_EMAIL, FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CONTEXT, FeedReaderContract.FeedEntry.COLUMN_NAME_IMAGE
                , FeedReaderContract.FeedEntry.COLUMN_NAME_LATITUE,FeedReaderContract.FeedEntry.COLUMN_NAME_LONGITUDE};

        String selection = FeedReaderContract.FeedEntry._ID + " = ? ";
        String[] selectionArgs = {String.valueOf(intent.getIntExtra("num",0))};
        Cursor c = db.query(FeedReaderContract.FeedEntry.TABLE_NAME_BORAD,projection1,selection,selectionArgs,null,null,"_id",null);
        if(c != null){
            if(c.moveToFirst()){
                TextView title = (TextView)findViewById(R.id.title2);
                TextView email = (TextView)findViewById(R.id.email2);
                TextView context = (TextView)findViewById(R.id.content2);
                TextView  location = (TextView)findViewById(R.id.location2);
                ImageView imageView = (ImageView)findViewById(R.id.imageView2);

                title.setText(c.getString(2));
                email.setText(c.getString(1));
                context.setText(c.getString(3));
                location.setText(getAddress(getApplicationContext(), c.getDouble(5),c.getDouble(6)));
                imageView.setImageURI(Uri.parse("file:///"+c.getString(4)));
            }
        }
    }

    public String getAddress(Context mContext, double lat, double lng) {
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);
                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    return currentLocationAddress;
                }
            }

        } catch (IOException e) {
            Toast.makeText(DetailActivity.this, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();
        }
        return "";
    }
}
