package com.example.soo.project;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.soo.project.R;


public class MyService extends Service {
    private MediaPlayer mp;
    public MyService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        if (mp != null) {
            mp.release();
            mp = null;
        }
        mp = MediaPlayer.create(this, R.raw.bgm);
        mp.setLooping(true); //반복재생
        mp.start(); //음악재생
        return 0;
    }

    public void onDestroy(){
        super.onDestroy();
        mp.stop(); //음악정지
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}