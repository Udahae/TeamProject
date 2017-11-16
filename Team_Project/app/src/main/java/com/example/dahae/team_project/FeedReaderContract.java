package com.example.dahae.team_project;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    public FeedReaderContract(){};

    public static final String DATABASE_NAME = "myDB3.db";

    public static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_USER + " ( " +
                    FeedEntry.COLUMN_NAME_EMAIL + " text PRIMART KEY, " +
                    FeedEntry.COLUMN_NAME_PASSWD + " text " + " ) ";

    public static  final String SQL_CREATE_TABLE_BORAD =
            "CREATE TABLE " + FeedEntry.TABLE_NAME_BORAD + " ( " +
            FeedEntry.COLUMN_NAME_EMAIL + " text , " +
            FeedEntry.COLUMN_NAME_PASSWD + " text, " + " ) ";


    public static final String SQL_DELETE_TABLE_USER =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME_USER;

    //데이터 베이스에 어떤 값이 들어가는지 조사 후 테이블 생성하기
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME_USER = "Users";
        public static final String TABLE_NAME_BORAD = "Notice_Borad";

        public static final String COLUMN_NAME_PASSWD = "passwd";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_IMAGE = "???";
    }
}
