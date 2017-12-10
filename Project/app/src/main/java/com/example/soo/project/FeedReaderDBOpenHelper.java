package com.example.soo.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by soo on 2017-12-03.
 */

public class FeedReaderDBOpenHelper extends SQLiteOpenHelper {
    Context context;

    public FeedReaderDBOpenHelper(Context context){
        super(context, FeedReaderContract.DATAVASE_NAME, null, 1);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db){
        try{
            Log.i("HOHO",FeedReaderContract.SQL_CREATE_MEMBER);
            Log.i("HOHO",FeedReaderContract.SQL_CREATE_TABLE_BORAD);
            db.execSQL(FeedReaderContract.SQL_CREATE_MEMBER);
            db.execSQL(FeedReaderContract.SQL_CREATE_TABLE_BORAD);
            Log.i("HOHO","생성 완료");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }

}
