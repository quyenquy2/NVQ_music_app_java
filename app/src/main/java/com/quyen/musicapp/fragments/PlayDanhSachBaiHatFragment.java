package com.quyen.musicapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quyen.musicapp.MyApplication;
import com.quyen.musicapp.R;
import com.quyen.musicapp.adapters.RecyclerAdapterPlayDanhSachBaiHat;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.services.MusicService;

import java.util.ArrayList;


public class PlayDanhSachBaiHatFragment extends Fragment {

    private View view;
    RecyclerView recyclerView;
    private ArrayList<BaiHat> baiHatArrayList;
    public RecyclerAdapterPlayDanhSachBaiHat recyclerAdapterPlayDanhSachBaiHat;

    public PlayDanhSachBaiHatFragment(ArrayList<BaiHat> baiHatArrayList) {
        this.baiHatArrayList = baiHatArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danh_sach_bai_hat, container, false);
        recyclerView = view.findViewById(R.id.fmPlayDanhSachBaiHat_recyclerView);
        recyclerAdapterPlayDanhSachBaiHat = new RecyclerAdapterPlayDanhSachBaiHat(getActivity(), baiHatArrayList, MusicService.positon);
        recyclerView.setAdapter(recyclerAdapterPlayDanhSachBaiHat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapterPlayDanhSachBaiHat.setOnItemClickListener(new RecyclerAdapterPlayDanhSachBaiHat.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                sendactiontoservice(7,position);
            }
        });
        return view;
    }
    public void sendactiontoservice(int action,int position)
    {
        Intent intent=new Intent(getContext(), MusicService.class);
        intent.putExtra("action",action);
        intent.putExtra("position",position);
        getContext().startService(intent);
    }
}