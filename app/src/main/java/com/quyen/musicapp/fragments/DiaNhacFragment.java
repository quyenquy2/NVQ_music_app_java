package com.quyen.musicapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quyen.musicapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaNhacFragment extends Fragment {
    public View view;
    CircleImageView circleImageView;

    public DiaNhacFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dia_nhac, container, false);
        circleImageView = view.findViewById(R.id.fmDiaNhac_CircleImageView);
        return view;
    }

    public void setImgDiaNhac(String url){
        Picasso.get().load(url).into(circleImageView);
    }

}