package com.quyen.musicapp.services;

import static com.quyen.musicapp.MyApplication.ChannelID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quyen.musicapp.R;
import com.quyen.musicapp.activities.PlayNhacActivity;
import com.quyen.musicapp.models.BaiHat;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {
    public static BaiHat baiHat;
    public static ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();
    public static MediaPlayer mediaPlayer;
    public static int positon;
    BaiHat baihat1=null;
    ArrayList<BaiHat> baiHatArrayList1=null;

    public static final int ACTION_PAUSE=1;
    public static final int ACTION_RESUME=2;
    public static final int ACTION_CLOSE=3;
    public static final int ACTION_NEXT=4;
    public static final int ACTION_PREVIOUS=5;
    public static final int ACTION_START=6;
    public static final int ACTION_SELECT=7;
    public static  boolean isplay=false;


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("tạo service thành công");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String noti=intent.getStringExtra("ccc");
        if (intent != null && intent.hasExtra("baiHat")) {
            if (intent.hasExtra("listBaiHat") && intent.hasExtra("baiHat")) {
                baiHatArrayList = new ArrayList<>();
                baiHat=new BaiHat();
                baihat1 = (BaiHat) intent.getParcelableExtra("baiHat");
                baiHatArrayList1 = intent.getParcelableArrayListExtra("listBaiHat");
                positon = intent.getIntExtra("position",0);
            } else if (intent.hasExtra("baiHat")) {
                baihat1 = (BaiHat) intent.getParcelableExtra("baiHat");
                baiHatArrayList1=null;
                positon=0;
            }
            if (baiHatArrayList1 != null)
            {
                baiHat=baihat1;
                baiHatArrayList=baiHatArrayList1;
                isplay=true;
            }
            else {
                if (baihat1 != null)
                {
                    baiHat=baihat1;
                    baiHatArrayList = new ArrayList<>();
                    baiHatArrayList.add(baiHat);
                    isplay=true;
                }
            }
            if(intent.hasExtra("baiHat")){
                handelActionmusic(ACTION_START);
                sendNotification(baiHat);
            }
        }

        int actionmusic=intent.getIntExtra("action",0);
        if (actionmusic != ACTION_SELECT) handelActionmusic(actionmusic);
        else {
            int index=intent.getIntExtra("position",1);
            select(index);
        }
        return START_NOT_STICKY;
    }

    private  void sendNotification(BaiHat song) {
        Intent intent1 =new Intent(this,PlayNhacActivity.class);
        intent1.putExtra("clicknotification", 9);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteViews remoteView=new RemoteViews(getPackageName(),R.layout.layout_push_notification_music);
        remoteView.setTextViewText(R.id.tv_song_name,song.getTenBaiHat());
        remoteView.setTextViewText(R.id.tv_artist,song.getTenCaSi());

        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.iconfloatingactionbutton);
        remoteView.setImageViewBitmap(R.id.img_song,bitmap);


        if (isplay)
        {
            remoteView.setOnClickPendingIntent(R.id.img_play,getpendingintent(this,ACTION_PAUSE));
            remoteView.setImageViewResource(R.id.img_play,R.drawable.ic_pause_black);
        } else {
            remoteView.setOnClickPendingIntent(R.id.img_play,getpendingintent(this,ACTION_RESUME));
            remoteView.setImageViewResource(R.id.img_play,R.drawable.ic_play_black);
        }
        remoteView.setOnClickPendingIntent(R.id.img_close,getpendingintent(this,ACTION_CLOSE));
        remoteView.setOnClickPendingIntent(R.id.img_next,getpendingintent(this,ACTION_NEXT));
        remoteView.setOnClickPendingIntent(R.id.img_previous,getpendingintent(this,ACTION_PREVIOUS));

        Notification notification=new NotificationCompat.Builder(this,ChannelID)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteView)
                .build();
        startForeground(1,notification);
    }

    PendingIntent getpendingintent(Context context, int action)
    {
        Intent intent=new Intent(this, MusicReciver.class);
        intent.putExtra("music",action);

        return PendingIntent.getBroadcast(context.getApplicationContext(),action,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("đóng service");
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    private void handelActionmusic(int action)
    {
        switch (action)
        {
            case ACTION_PAUSE:
                pause();
                break;
            case ACTION_RESUME:
                resume();
                break;
            case ACTION_CLOSE:
                stopSelf();
                sendactiontiactivity(ACTION_CLOSE);
                break;
            case ACTION_NEXT:
                next();
                break;
            case ACTION_PREVIOUS:
                previous();
                break;
            case ACTION_START:
                start();
                break;
        }
    }

    private void previous() {
        if ((positon - 1) >= 0) {
            mediaPlayer.stop();
            isplay=true;
            positon--;
            baiHat = baiHatArrayList.get(positon);
            play(baiHat.getLinkBaiHat());
            sendactiontiactivity(ACTION_PREVIOUS);
            sendNotification(baiHat);
        } else {
            mediaPlayer.stop();
            isplay=true;
            positon = baiHatArrayList.size() - 1;
            baiHat = baiHatArrayList.get(positon);
            play(baiHat.getLinkBaiHat());
            sendactiontiactivity(ACTION_PREVIOUS);
            sendNotification(baiHat);
        }
    }

    private void start() {
        play(baiHat.getLinkBaiHat());
        sendactiontiactivity(ACTION_START);
        sendNotification(baiHat);
    }

    private void select(int index){
        mediaPlayer.stop();
        isplay=true;
        positon=index;
        baiHat = baiHatArrayList.get(positon);
        play(baiHat.getLinkBaiHat());
        sendactiontiactivity(ACTION_NEXT);
        sendNotification(baiHat);
    }

    private void next() {
        if (positon<(baiHatArrayList.size()-1))
        {
            mediaPlayer.stop();
            isplay=true;
            positon++;
            baiHat = baiHatArrayList.get(positon);
            play(baiHat.getLinkBaiHat());
            sendactiontiactivity(ACTION_NEXT);
            sendNotification(baiHat);
        }
        else
        {
            mediaPlayer.stop();
            isplay=true;
            positon=0;
            baiHat = baiHatArrayList.get(positon);
            play(baiHat.getLinkBaiHat());
            sendactiontiactivity(ACTION_NEXT);
            sendNotification(baiHat);
        }

    }

    public void resume() {
        mediaPlayer.start();
        isplay=true;
        sendactiontiactivity(ACTION_RESUME);
        sendNotification(baiHat);
    }

    public void pause()
    {
        mediaPlayer.pause();
        isplay=false;
        //baiHat = baiHatArrayList.get(positon);
        sendactiontiactivity(ACTION_PAUSE);
        sendNotification(baiHat);
    }

    private void sendactiontiactivity(int action)
    {
        Intent intent=new Intent("senddatatoactivity");
        intent.putExtra("actionmusic",action);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    public static void clearplayer()
    {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    public void play(String link)
    {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();}
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.reset();
                }
            });
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    next();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
