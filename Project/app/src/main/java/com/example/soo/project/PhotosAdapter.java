package com.example.soo.project;

/**
 * Created by leesangmu1 on 2017-11-24.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

// 메인 화면에 추가되도록 하는 apater부분 여기서 실제 내용이 입력이 된다.
public class PhotosAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Photo> photos;

    // 1
    public PhotosAdapter(Context context, List<Photo> photos) {
        this.mContext = context;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Photo getItem(int i) {
        return photos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1
        final Photo book = photos.get(position);
        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.activity_photos_adapter, null);
        }

        // 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_cover_art);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview_photo_name);
        final TextView authorTextView = (TextView)convertView.findViewById(R.id.textview_photo_author);

//        // 4

        imageView.setImageURI(Uri.parse("file:///"+book.getImageUrl()));
        nameTextView.setText(book.getEmail());
        authorTextView.setText(book.getTitle());

        return convertView;
    }

}