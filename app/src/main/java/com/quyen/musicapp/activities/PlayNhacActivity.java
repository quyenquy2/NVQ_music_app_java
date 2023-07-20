package com.quyen.musicapp.activities;

import static com.quyen.musicapp.services.MusicService.ACTION_NEXT;
import static com.quyen.musicapp.services.MusicService.ACTION_PAUSE;
import static com.quyen.musicapp.services.MusicService.ACTION_PREVIOUS;
import static com.quyen.musicapp.services.MusicService.ACTION_RESUME;
import static com.quyen.musicapp.services.MusicService.ACTION_START;
import static com.quyen.musicapp.services.MusicService.baiHat;
import static com.quyen.musicapp.services.MusicService.baiHatArrayList;
import static com.quyen.musicapp.services.MusicService.isplay;
import static com.quyen.musicapp.services.MusicService.mediaPlayer;
import static com.quyen.musicapp.services.MusicService.positon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quyen.musicapp.R;
import com.quyen.musicapp.adapters.ViewPagerAdapterPlayNhac;
import com.quyen.musicapp.fragments.DiaNhacFragment;
import com.quyen.musicapp.fragments.PlayDanhSachBaiHatFragment;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.services.MusicService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SeekBar seekbar;
    private TextView txtTimeStart, txtTimeEnd;
    private ImageButton  btnPrev, btnPlay, btnNext;
    private ViewPager viewPager;

    private PlayDanhSachBaiHatFragment playDanhSachBaiHatFragment;
    private DiaNhacFragment diaNhacFragment;
    private ViewPagerAdapterPlayNhac viewPagerAdapterPlayNhac;
    private boolean goback;



    private BroadcastReceiver broadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int actionms=intent.getIntExtra("actionmusic",0);

            handleMusic(actionms);
        }
    };

    private void handleMusic(int actionms) {
        switch (actionms){
            case MusicService.ACTION_START:
                handelstart();
                break;
            case MusicService.ACTION_PAUSE:
                handelpause();
                break;
            case MusicService.ACTION_NEXT:
                handelnext();
                break;
            case MusicService.ACTION_PREVIOUS:
                handelprevious();
                break;
            case MusicService.ACTION_CLOSE:
                handelclose();
                break;
            case MusicService.ACTION_RESUME:
                handelresume();
                break;
        }
    }

    private void handelprevious() {
        customView();
        loadtime();
    }

    private void handelnext() {
        customView();
        loadtime();
    }

    private void handelclose() {
        finish();
    }

    private void handelresume() {
        btnPlay.setImageResource(R.drawable.iconpause);
        loadtime();
    }

    private void handelpause() {
        btnPlay.setImageResource(R.drawable.iconplay);
        loadtime();
    }

    private void handelstart() {
        TimeSong();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("senddatatoactivity"));

        Intent intent = getIntent();
        if (intent.hasExtra("clicknotification")) {
            goback=true;
            mapping();
            init();
            eventClick();
            TimeSong();
            goback=false;
        } else  {
            mapping();
            init();
            eventClick();
        }

    }

    private void eventClick() {

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.iconplay);
                    sendactiontoservice(ACTION_PAUSE);
                } else {
                    btnPlay.setImageResource(R.drawable.iconpause);
                    sendactiontoservice(ACTION_RESUME);
                }

                loadtime();

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendactiontoservice(ACTION_NEXT);
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendactiontoservice(ACTION_PREVIOUS);
            }
        });

        loadtime();
    }

    private void setImgDiaNhac(String hinhanh) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    diaNhacFragment.setImgDiaNhac(hinhanh);
                    handler.removeCallbacks(this);
                } catch (Exception e) {
                    handler.postDelayed(this, 500);
                }
            }
        }, 500);
    }

    private void mapping() {
        toolbar = findViewById(R.id.activityPlayNhac_toolBar);
        seekbar = findViewById(R.id.activityPlayNhac_seekBar);
        txtTimeStart = findViewById(R.id.activityPlayNhac_timeStart);
        txtTimeEnd = findViewById(R.id.activityPlayNhac_timeEnd);
        btnPrev = findViewById(R.id.activityPlayNhac_btnPrevious);
        btnPlay = findViewById(R.id.activityPlayNhac_btnPlay);
        btnNext = findViewById(R.id.activityPlayNhac_btnNext);
        viewPager = findViewById(R.id.activityPlayNhac_viewPager);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back_left_arrow);

        playDanhSachBaiHatFragment = new PlayDanhSachBaiHatFragment(baiHatArrayList);
        diaNhacFragment = new DiaNhacFragment();

        viewPagerAdapterPlayNhac = new ViewPagerAdapterPlayNhac(getSupportFragmentManager());
        viewPagerAdapterPlayNhac.addFragment(playDanhSachBaiHatFragment);
        viewPagerAdapterPlayNhac.addFragment(diaNhacFragment);
        viewPager.setAdapter(viewPagerAdapterPlayNhac);

        customView();

    }

    private void customView() {
        if (baiHat.getTenBaiHat() != null && baiHat.getHinhAnhBaiHat() != null) {
            getSupportActionBar().setTitle(baiHat.getTenBaiHat());

            if (!goback) TimeSong();

            if(isplay)
            {
                btnPlay.setImageResource(R.drawable.iconpause);
            }
            else {
                btnPlay.setImageResource(R.drawable.iconplay);
            }
            diaNhacFragment = (DiaNhacFragment) viewPagerAdapterPlayNhac.getItem(1);
            setImgDiaNhac(baiHat.getHinhAnhBaiHat());
        }
    }


    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekbar.setMax(mediaPlayer.getDuration());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
    public void sendactiontoservice(int action)
    {
        Intent intent=new Intent(this,MusicService.class);
        intent.putExtra("action",action);
        startService(intent);
    }
    public void loadtime()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null)
                {
                    try {
                        if (mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
                            seekbar.setProgress(mediaPlayer.getCurrentPosition());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                            txtTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                            handler.postDelayed(this, 500);
                        }
                    } catch (Exception e){}
                }
            }
        }, 500);
    }
}