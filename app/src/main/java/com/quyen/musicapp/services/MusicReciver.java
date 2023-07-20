package com.quyen.musicapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MusicReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionmusic=intent.getIntExtra("music",0);

        Intent intent1=new Intent(context,MusicService.class);
        intent1.putExtra("action",actionmusic);

        context.startService(intent1);
    }
}
