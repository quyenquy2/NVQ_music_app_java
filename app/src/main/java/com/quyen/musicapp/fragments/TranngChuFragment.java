package com.quyen.musicapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quyen.musicapp.R;
import com.quyen.musicapp.activities.MainActivity;
import com.quyen.musicapp.activities.TimKiemActivity;

public class TranngChuFragment extends Fragment {
    FloatingActionButton fab;


    public TranngChuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //fab = getActivity().findViewById(R.id.fab_btn);
        return inflater.inflate(R.layout.fragment_tranng_chu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        fab = getActivity().findViewById(R.id.fab_btn);
//
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
//                Intent intent =new Intent(getActivity(),TimKiemActivity.class);
//                startActivity(intent);
//            }
//        });

        SwipeRefreshLayout swipeRefreshLayout = getActivity().findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}