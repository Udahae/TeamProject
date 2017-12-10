package com.example.soo.project;

import android.provider.BaseColumns;

/**
 * Created by soo on 2017-12-02.
 */

public class FeedReaderContract {

    public FeedReaderContract(){};
    public static final String DATAVASE_NAME = "member.db";

    public static final String SQL_CREATE_MEMBER =
            "create table " + FeedEntry.TABLE_NAME_Member + " ( " +
                    FeedEntry.COLUMN_NAME_EMAIL + " text not null primary key , " +
                    FeedEntry.COLUMN_NAME_PASSWORD + " text not null , " +
                    FeedEntry.COLUMN_NAME_PASSSIGN + " text not null , " +
                    FeedEntry.COLUMN_NAME_NAME + " text not null )" ;
//                    FeedEntry.COLUMN_NAME_GROUP + " varchar(40) ) ";

//    String sql = "create table memberJoin ( "+
//            "email varchar(40) not null primary key, "+
//            "pass varchar(72) not null, "+
//            "passCheck varchar(72) not null, "+
//            "name varchar(40) not null "+
//            ");";

    public static  final String SQL_CREATE_TABLE_BORAD =
            "create table " + FeedEntry.TABLE_NAME_BORAD + " ( " +
                    FeedEntry._ID + " integer primary key autoincrement, "+
                    FeedEntry.COLUMN_NAME_EMAIL + " text not null , " +
//                    FeedEntry.COLUMN_NAME_GROUP + " text, " +
                    FeedEntry.COLUMN_NAME_TITLE + " text, " +
                    FeedEntry.COLUMN_NAME_CONTEXT + " text, "+
                    FeedEntry.COLUMN_NAME_IMAGE+" text, " +
                    FeedEntry.COLUMN_NAME_LATITUE + " real, "+
                    FeedEntry.COLUMN_NAME_LONGITUDE + " real)";

    public  static final String SQL_DELETE_MEMBER =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME_Member;

    public static final String SQL_DELETE_TABLE_BORAD =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME_BORAD;

    public  static abstract class  FeedEntry implements BaseColumns {
        public static final  String TABLE_NAME_Member = "memberJoin";
        public static final  String COLUMN_NAME_EMAIL = "email";
        public static final  String COLUMN_NAME_PASSWORD = "pass";
        public static final  String COLUMN_NAME_PASSSIGN = "passCheck";
        public static final  String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GROUP = "group";

        public static final String TABLE_NAME_BORAD = "notice_borad";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTEXT = "context";
        public static final String COLUMN_NAME_LATITUE = "latitue";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}
