package com.quyen.musicapp.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quyen.musicapp.R;
import com.quyen.musicapp.activities.TimKiemActivity;
import com.quyen.musicapp.adapters.RecyclerAdapterBaiHatYeuThich;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemFragment extends Fragment {
    private View view;
    RecyclerView recyclerView;
    EditText edt;
    Button gohome;
    ArrayList<BaiHat> BaihatArrayList;
    RecyclerAdapterBaiHatYeuThich adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tim_kiem, container, false);
        recyclerView = view.findViewById(R.id.listviewtimkiem);
        edt = view.findViewById(R.id.timkiem);
        gohome= view.findViewById(R.id.gohomend);
        getListBaiHatTimKiem("");
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getListBaiHatTimKiem(s.toString());
            }
        });

        return view;
    }

    private void getListBaiHatTimKiem(String keyword) {
        if ( BaihatArrayList != null) {
            BaihatArrayList.clear();
            recyclerView.setAdapter(null);
        }
        DataService dataService = ApiService.getService();
        Call<List<BaiHat>> callback = dataService.getBaiHatTheoKeyword(keyword);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                BaihatArrayList = (ArrayList<BaiHat>) response.body();
                if (BaihatArrayList.size() > 0) {
                    adapter = new RecyclerAdapterBaiHatYeuThich(getContext(), BaihatArrayList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
                Log.d("tag", "Load data baihat fail");
            }
        });
    }
}
